using System.Net.Sockets;
using System.Text;

namespace Server
{
    public partial class Server
    {
        public class HandleClient
        {
            public TcpClient ClientSocket { get; private set; }
            readonly string clientId;
            byte[] _buffer = new byte[4096];
            public bool Active { get; private set; } = true;


            public HandleClient(TcpClient clientSocket, string clientId)
            {
                ClientSocket = clientSocket;
                this.clientId = clientId;

                ClientSocket.GetStream().BeginRead(_buffer,
                                                0,
                                                _buffer.Length,
                                                Server_MessageReceived,
                                                null);
            }


            private void Server_MessageReceived(IAsyncResult ar)
            {
                if (!ar.IsCompleted)
                    return;


                var bytesIn = ClientSocket.GetStream().EndRead(ar);
                if (bytesIn == 0)
                {
                    Console.WriteLine(" >> " + "Client No:" + clientId + " closed!");
                    ClientSocket.Close();
                    Active = false;
                    return;
                }
                var tmp = new byte[bytesIn];
                Array.Copy(_buffer, 0, tmp, 0, bytesIn);
                var str = Encoding.ASCII.GetString(tmp);

                Console.WriteLine(" >> From Client No:" + clientId + " : " + str);
                BroadcastMesssage($"{clientId} => " + str);

                Array.Clear(_buffer, 0, _buffer.Length);
                ClientSocket.GetStream().BeginRead(_buffer,
                                                           0,
                                                           _buffer.Length,
                                                           Server_MessageReceived,
                                                           null);

            }
        }
    }
}