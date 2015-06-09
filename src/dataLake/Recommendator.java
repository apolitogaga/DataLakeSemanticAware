package dataLake;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import org.apache.thrift.server.THsHaServer.Args;

import com.sun.glass.events.KeyEvent;

public class Recommendator {
	SPARQLManager sparq;
	public Recommendator(){
		sparq =  new SPARQLManager();
	}
	
	public void recommendate(){
		
		ArrayList<ArrayList<String>> res = sparq.selectMovieBestRated();
		ArrayList<String> subjects = res.get(0);
		ArrayList<String> predicates = res.get(1);
		ArrayList<String> literals = res.get(2);
		
		for (int i = 1; i < predicates.size(); i++)
			System.out.println("We recommend watching :"+ literals.get(i) +" with rating: "+ predicates.get(i) );
		
	}
	public void waitForEnter() {

System.out.println("WElcome to the NETFLIX like recommendator, please follow the instructions and try to be a good chap.");
		int i=0;
		do{
			System.out.println("Press a button to get a recommendation.");
			try {
		        System.in.read();
		        this.recommendate();
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		}while(i<10);
	}
}
