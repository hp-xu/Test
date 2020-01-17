package com.ipackage;

public class DetTestA {

    public static int inverse(int[] s) {
        int t = 0;
        for (int i = 0; i < s.length - 1; i++)
            for (int j = i + 1; j < s.length; j++)
                if (s[i] > s[j])
                    t++;
        return t;
    }

    public static int factorial(int n) {
        return n == 0 ? 1 : n * factorial(n - 1);
    }

    public static int[] order(int n, int index) {
        if (n < 1)
            throw new IllegalArgumentException("The size of number array could not less than 1");
        if (index >= factorial(n) || index < 0)
            throw new IllegalArgumentException("The index could not be reached");
        int[] nums = new int[n];//java数组在初始化时自动置0，不需要手动置0
        fillArray(n, nums, index);
        return nums;
    }

    private static void fillArray(int n, int[] nums, int index) {
        if (n == 0)
            return;
        int fac = factorial(n - 1);
        int p = index / fac + 1;
        int i = -1;//此处为填充还未填充的位置，即值为0的位置
        while (p > 0) {
            if (nums[++i] == 0)
                p--;
        }
        nums[i] = n;
        fillArray(n - 1, nums, index % fac);
    }

    public static double det(double[][] A) {
        float det = 0f;
        int m = A.length;
        int n = A[0].length;
        if (m != n)
            return det;
        int fac = factorial(n);
        for (int i = 0; i < fac; i++) {
            // 返回一个排列
            int[] order = order(n, i);
            // 逆序数确定项的正负性
            float item = (inverse(order) & 1) == 0 ? 1 : -1;
            for (int j = 0; j < n; j++)
                item *= A[j][order[j]-1];
            det += item;
        }
        return det;
    }

    public static void main( String [] args )
    {
        System.out.println( factorial(10) );

//        double[][] test = {{1, 2, 3, 4}, {2, 3, 4, 1}, {3, 4, 1, 2}, {4, 1, 2, 3}} ;
//
//        double[][] A = new double[2048][2048] ;
//
//        for( int i = 0 ; i < A.length ; i++ )
//        {
//            for( int j = 0 ; j < A.length ; j++ )
//            {
//                A[i][j] = (int)(Math.random()*1000000000)/10000.0 ;
//            }
//        }
//
//        long curtime = System.currentTimeMillis() ;
//        double det = det( test ) ;
//        System.out.println( det +" ; " + ( System.currentTimeMillis()-curtime ) ) ;
    }
}
