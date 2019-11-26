package FileServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public final static String SERVER_IP = "localhost";
    public final static int SERVER_PORT = 9999;
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
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException{
    	ArrayList<Student> students = new ArrayList<Student>();
    	InputData(students);
        Socket socket = null;
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT); 
            System.out.println("Connected: " + socket);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            OutputStream os = socket.getOutputStream();
            int soluong = students.size();
            os.write(soluong);
            for(Student k:students)
            {
            	oos.writeObject(k);
            }
            int dem =0 ;
            System.out.println("Top 5 sinh vien co diem trung binh cao nhat: ");
            while(dem<5)
            {
	            Student i = new Student();
	            i =	(Student) ois.readObject();
	            i.OutputStudent();
	            dem = dem+1;
            }
        } catch (IOException ie) {
            System.out.println("Can't connect to server");
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}