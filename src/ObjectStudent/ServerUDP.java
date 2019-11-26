package ObjectStudent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
 
public class ServerUDP {
 
    public final static int SERVER_PORT = 9999;
    public final static byte[] BUFFER = new byte[4096];
    public static void main(String[] args) {
        DatagramSocket ds = null;
        ArrayList<Student> students = new ArrayList<Student>();
        ArrayList<Student> studentkq = new ArrayList<Student>();
        ArrayList<Student> sendstudent = new ArrayList<Student>();
        ActionFile AF = new ActionFile();
        studentkq = AF.LoadFileStudent("DataStudent.dat");
        try {
            System.out.println("Binding to port " + SERVER_PORT + ", please wait  ...");
            ds = new DatagramSocket(SERVER_PORT);
            System.out.println("Server started ");
            System.out.println("Waiting for messages from Client ... ");
            DatagramPacket dp = new DatagramPacket(BUFFER, BUFFER.length);
            ds.receive(dp);
            ByteArrayInputStream bais = new ByteArrayInputStream(dp.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            students = (ArrayList<Student>) ois.readObject();
        	for(Student i: students)
        		studentkq.add(i);
        	Collections.sort(studentkq, new Comparator<Student>()
    		{
    			@Override
                public int compare(Student o1, Student o2) {
                    if(o1.getMarkavge() - o2.getMarkavge() < 0)
                    	return 1;
                    else  if(o1.getMarkavge() - o2.getMarkavge() == 0)
                    	return 0;
                    else
                    	return -1;
                }
    		});
        	AF.SaveFileStudent(studentkq, "DataStudent.dat");
    		System.out.println("Thong tin sinh vien..!");
    		int dem = 0;
    		for(Student i : studentkq)
    		{
    			i.OutputStudent();
    			if(dem<3)
    			{
    				sendstudent.add(i);
    				dem=dem+1;
    			}
    		}
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(sendstudent);
            byte[] data = baos.toByteArray();
            dp = new DatagramPacket(data, data.length, dp.getAddress(), dp.getPort());
            ds.send(dp);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
    }
}