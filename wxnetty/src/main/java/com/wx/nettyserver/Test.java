package com.wx.nettyserver;

import java.io.*;
import java.util.Scanner;

class Test {



    public static void main(String[] args) throws IOException {
    	/*  Scanner sc = new Scanner(System.in);
    	 int m[] = getArray(Integer.parseInt(sc.nextLine()));
    	  int n = Integer.parseInt(sc.nextLine());*/
    	  int h[] = getLastValue(getArray(2),13);
    	  System.out.println(h[0]);
    }
    
    private static int[] getArray(int x){
    	int[] test = new int[x];
    	for(int t=1;t<=x;t++){
    		test[t-1] = t;
    	}
    	return test;
    }
    
    private static int[] getLastValue(int[] m,int n){
    	int length = m.length;
       if(length>1){
    	int x[] = new int[length-1];
    	int y = length/n>0? n:n%length == 0?length:n%length;
    	for(int h=y-1,ds=0;h>0;h--,ds++){
    	     x[x.length-h]=m[ds];
        }
        for(int d=y,k=0;d<length;d++,k++){
    	  x[k]=m[d];
        }
        return  getLastValue(x,n);
      } else {
    	 return  m;
      }
    }
}
