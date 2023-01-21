using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace Server
{
    public partial class Server
    {
        public static List<HandleClient> handleClients = new List<HandleClient>();

        [Obsolete]
        public static int Main(string[] args)
        {
            TcpListener serverSocket = new TcpListener(8888);
            int counter = 0;

            Console.WriteLine(" >> " + "Server Started");

            serverSocket.Start();

            counter = 0;
            try
            {
                while (true)
                {
                    counter += 1;
                    var clientSocket = serverSocket.AcceptTcpClient();
                    Console.WriteLine(" >> " + "Client No:" + Convert.ToString(counter) + " started!");
                    HandleClient client = new HandleClient(clientSocket, Convert.ToString(counter));
                    handleClients.Add(client);
                }
            }
            catch (Exception)
            {
            }

            serverSocket.Stop();
            Console.WriteLine(" >> " + "exit");
            return 0;
        }

        public static void BroadcastMesssage(string str)
        {
            handleClients = handleClients.Where(client => client.Active).ToList();
            var bytes = Encoding.ASCII.GetBytes(str);
            handleClients.ForEach(client =>
            {
                try
                {
                    client.ClientSocket.GetStream().WriteAsync(bytes);
                }
                catch (Exception) { }
            });
        }
    }
}