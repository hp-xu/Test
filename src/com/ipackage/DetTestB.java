package com.ipackage;

public class DetTestB {

    public static double det(double[][] p, int n) {
        if (n == 1)  // 如果是一阶行列式,直接返回该元素
            return p[0][0];

        double sum = 0; // 累加求和变量

        for (int j = 0; j < n; j++) {// 遍历最后一行各元素,p[n - 1][j]
            int pt = (n - 1) + j;  // 符号判断指数

            double[][] p1 = new double[n][n];

            // 此过程是为了把行列式存放到临时数组中，不改变但前行列式
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    p1[row][col] = p[row][col];
                }
            }

            // 此过程，是为了将元素 p[n-1][j] 所在列之后的每一列向前移动一列，为后面获取该元素对应的余子式做准备
            for (int index = 0; index < n - 1; index++) {
                for (int index1 = j; index1 < n - 1; index1++) {
                    p1[index][index1] = p1[index][index1 + 1];
                }
            }

            // 此过程，截取临时数组 p1 左上角 n-1  阶行列式，提取元素 p[n-1][j] 的余子式
            double[][] temp = new double[n - 1][n - 1];
            for (int row = 0; row < n - 1; row++) {
                for (int col = 0; col < n - 1; col++) {
                    temp[row][col] = p1[row][col];
                }
            }

            // 求和：sum += 元素 * 符号 * 余子式
            // 因为，余子式是去除某一元素所在的行和列之后剩下的元素构成的 n-1 阶行列式
            // 即，余子式本质还是行列式，所以需要递归求行列式的值
            sum += p[n - 1][j] * Math.pow(-1, pt) * det(temp, n - 1);
            // System.out.println(p[n - 1][j] + " * " + Math.pow(-1, pt) + " * " + det(p1, n - 1));
//            System.out.println( sum );
        }

        return sum;
    }


    public static void main( String [] args )
    {
        double[][] test = {{1, 2, 3, 4}, {2, 3, 4, 1}, {3, 4, 1, 2}, {4, 1, 2, 3}} ;

        double[][] A = new double[15][15] ;

        for( int i = 0 ; i < A.length ; i++ )
        {
            for( int j = 0 ; j < A.length ; j++ )
            {
                A[i][j] = (int)(Math.random()*1000000000)/10000.0 ;
            }
        }

        long curtime = System.currentTimeMillis() ;
        double det = det( A , A.length ) ;
        System.out.println( det +" ; " + ( System.currentTimeMillis()-curtime ) ) ;
    }

}
