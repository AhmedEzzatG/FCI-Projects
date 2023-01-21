using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Windows.Forms;

namespace WindowsFormsApp1
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            FormBorderStyle = FormBorderStyle.None;
            WindowState = FormWindowState.Maximized;
        }

        List<string> files = new List<string>();
        int currentImage = 0;
        int currentMode = 3;
        private void upload_images(object sender, EventArgs e)
        {
            OpenFileDialog file = new OpenFileDialog();
            file.Title = "select images";
            file.Multiselect = true;
            file.ValidateNames = true;
            file.Filter = "Image Files(*.png;*.JPG)|*.png;*.JPG";
            if (file.ShowDialog() == DialogResult.OK)
            {
                foreach (string image in file.FileNames)
                {
                    FileInfo fi = new FileInfo(image); 
                    string path =  fi.FullName;
                    if (files.Contains(path)) continue;
                    imageList1.Images.Add(image, new Bitmap(path));
                    ListViewItem item = new ListViewItem();
                    item.ImageIndex = files.Count;
                    item.Text = fi.Name;
                    listView1.Items.Add(item);
                    files.Add(path);
                }
            }
        }

        private PictureBox get_image(int index)
        {
            PictureBox p1 = new PictureBox();
            p1.SizeMode = PictureBoxSizeMode.Zoom;
            p1.Image = Image.FromFile(files[index]);
            p1.MouseClick += Form1_MouseClick;
            return p1;
        }
        private void show_items(object sender, EventArgs e)
        {
            if (currentMode == 3)
            {
                if (sender == singlePictureModeMenuItem)
                {
                    currentMode = 0;
                }
                else if (sender == multiPictureModeMenuItem)
                {
                    currentMode = 1;
                }
                else return;
                changeMenuItems();
            }
            if (currentMode == 2)
                return;
            imagesPanel.Controls.Clear();
            foreach (ListViewItem item in listView1.SelectedItems)
            {
                imagesPanel.Controls.Add(get_image(item.ImageIndex));
            }
            int cnt = imagesPanel.Controls.Count;
            if (cnt == 0)
            {
                return;
            }
            if (currentMode == 0)
            {
                cnt = 1;
            }
            int height = imagesPanel.Size.Height;
            int width = imagesPanel.Size.Width;
            height -= 10;
            width -= 30;
            int x = 1;
            while (x * x < cnt) x++;
            height = Math.Max(0, height / x);
            width = Math.Max(0, width / x);
            for (int i = 0; i < cnt; i++)
            {
                imagesPanel.Controls[i].Size = new Size(width, height);
            }
        }
        private void clearButton_Click(object sender, EventArgs e)
        {
            listView1.Clear();
            imageList1.Images.Clear();
            files.Clear();
            imagesPanel.Controls.Clear();
        }
        private void Form1_MouseClick(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
            {
                contextMenuStrip1.Show(new Point(e.X, e.Y));
            }
        }

        private void slideshowMode(object sender, EventArgs e)
        {
            if (files.Count == 0)
            {
                MessageBox.Show("Please upload at Least 1 picture");
                return;
            }
            currentMode = 2;
            changeMenuItems();
            currentImage = 0;
            timer1.Stop();
            timer1.Interval = 1000;
            timer1.Start();
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            if (currentImage >= files.Count)
            {
                stopToolStripMenuItem_Click(sender, e);
                return;
            }
            pictureBox1 = get_image(currentImage);
            pictureBox1.Size = imagesPanel.Size;
            imagesPanel.Controls.Clear();
            imagesPanel.Controls.Add(pictureBox1);
            currentFileSatus.Text = files[currentImage];
            currentImage++;
        }

        private void stopToolStripMenuItem_Click(object sender, EventArgs e)
        {
            timer1.Stop();
            currentFileSatus.Text = "";
            currentMode = 3;
            changeMenuItems();
        }

        private void closeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void changeMenuItems()
        {
            listView1.MultiSelect = (currentMode != 0);
            singlePictureModeMenuItem.Visible = (currentMode == 3);
            multiPictureModeMenuItem.Visible = (currentMode == 3);
            slideshowModeMenuItem.Visible = (currentMode == 3);
            stopToolStripMenuItem.Visible = (currentMode != 3);
            currentModeSatus.Text = "Current Mode is : ";
            switch (currentMode)
            {
                case 0:
                    currentModeSatus.Text += "single Picture";
                    break;
                case 1:
                    currentModeSatus.Text += "multi-Pictures";
                    break;
                case 2:
                    currentModeSatus.Text += "slide show";
                    break;
                default:
                    currentModeSatus.Text += "standby";
                    break;
            }
        }
    }
}