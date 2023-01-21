using System.Net.Sockets;
using System.Text;

namespace Client
{
    public partial class Form1 : Form
    {

        TcpClient _client;


        byte[] _buffer = new byte[4096];

        public Form1()
        {
            InitializeComponent();
            _client = new TcpClient();


            _client.Connect("127.0.0.1", 8888);

            _client.GetStream().BeginRead(_buffer,
                                                       0,
                                                       _buffer.Length,
                                                       Server_MessageReceived,
                                                       null);
        }

        private void Server_MessageReceived(IAsyncResult ar)
        {
            if (!ar.IsCompleted) return;

            var bytesIn = _client.GetStream().EndRead(ar);
            //if (bytesIn == 0) return;
            if (bytesIn > 0)
            {
                var str = ReadFromBuffer(bytesIn);

                BeginInvoke(() =>
                {
                    listBox1.Items.Add(str);
                    listBox1.SelectedIndex = listBox1.Items.Count - 1;
                });
            }

            Array.Clear(_buffer);
            _client.GetStream().BeginRead(_buffer,
                                                       0,
                                                       _buffer.Length,
                                                       Server_MessageReceived,
                                                       null);
        }

        private string ReadFromBuffer(int len)
        {
            byte[] tmp = new byte[4096];
            Array.Copy(_buffer, tmp, len);
            return Encoding.ASCII.GetString(tmp);
        }

        private void Send_Click(object sender, EventArgs e)
        {
            var msg = Encoding.ASCII.GetBytes(textBox1.Text);
            _client.GetStream().WriteAsync(msg);

            textBox1.Text = "";
            textBox1.Focus();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
           
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void listBox1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }
    }
}