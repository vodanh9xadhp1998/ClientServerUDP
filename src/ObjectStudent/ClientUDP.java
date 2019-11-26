package ObjectStudent;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;
 
public class ClientUDP {
 
    public final static String SERVER_IP = "localhost";
    public final static int SERVER_PORT = 9999; 
    public final static byte[] BUFFER = new byte[4096];
    public static void InputData(ArrayList<Student> students) {
		int n;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.print("Ban co muon nhap them khong sinh vien khong..?\n(1)Co\n(0)Khong\nMoi ban chon: ");
			n = sc.nextInt();
			if (n == 1) 
			{
				Student student = new Student();
				student.InputStudent();
				students.add(student);
			}
		} while (n != 0);
	}
    public static void main(String[] args) {
        DatagramSocket ds = null;
        ArrayList<Student> students = new ArrayList<Student>();
        ArrayList<Student> studentkq = new ArrayList<Student>();
        InputData(students);
        try {
            ds = new DatagramSocket();
            System.out.println("Client started ");
            InetAddress server = InetAddress.getByName(SERVER_IP);
           //gui Object Student
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(students);
            byte[] data = baos.toByteArray();
            DatagramPacket dp = new DatagramPacket(data, data.length, server, SERVER_PORT);
            ds.send(dp);
            //nhan Object Student
            dp = new DatagramPacket(BUFFER, BUFFER.length);
            ds.receive(dp);
            ByteArrayInputStream bais = new ByteArrayInputStream(dp.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            studentkq = (ArrayList<Student>) ois.readObject();
            System.out.println("Top 3 SV diem cao nhat..!");
            for(Student i : studentkq)
            	i.OutputStudent();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e);
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
    }
}
