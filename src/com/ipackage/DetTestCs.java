package com.ipackage;

import java.math.BigDecimal;

public class DetTestCs {

    public static double det(double[][] p, int n) {
        if (n == 1) return p[0][0];

        double exChange = 1.0; // 记录行列式中交换的次数
        boolean isZero = false; // 标记行列式某一行的最右边一个元素是否为零

        for (int i = 0; i < n; i ++) {// i 表示行号
            if (p[i][n - 1] != 0) { // 若第 i 行最右边的元素不为零
                isZero = true;

                if (i != (n - 1)) { // 若第 i 行不是行列式的最后一行
                    for (int j = 0; j < n; j ++) { // 以此交换第 i 行与第 n-1 行各元素
                        double temp = p[i][j];
                        p[i][j] = p[n - 1][j];
                        p[n - 1][j] = temp;

                        exChange *= -1.0;
                    }
                }

                break;
            }
        }

        if (!isZero) return 0; // 行列式最右边一列元素都为零，则行列式为零。

        BigDecimal bd1 = null ;
        BigDecimal bd2 = null ;

        BigDecimal bd3 = null ;
        BigDecimal bd4 = null ;

        for (int i = 0; i < (n - 1); i ++) {
            // 用第 n-1 行的各元素，将第 i 行最右边元素 p[i][n-1] 变换为 0，
            // 注意：i 从 0 到 n-2，第 n-1 行的最右边元素不用变换
            if (p[i][n - 1] != 0) {
                bd1 = new BigDecimal( p[i][n - 1] ) ;
                bd2 = new BigDecimal( p[n - 1][n - 1] ) ;
                // 计算第  n-1 行将第 i 行最右边元素 p[i][n-1] 变换为 0的比例
//                double proportion = p[i][n - 1] / p[n - 1][n - 1];
                BigDecimal su = bd1.divide( bd2 , 10 , BigDecimal.ROUND_UP ) ;

                for (int j = 0; j < n; j ++) {
//                    p[i][j] += p[n - 1][j] * (-proportion);
                    bd3 = new BigDecimal( p[i][j] ) ;
                    bd4 = new BigDecimal( p[n - 1][j] ) ;
                    p[i][j] = bd3.subtract( bd4.multiply( su ) ).doubleValue() ;
                }
            }
        }

//        return exChange * p[n - 1][n - 1] * det(p, (n - 1)) ;
        return ( new BigDecimal( exChange ) ).multiply( new BigDecimal( p[n - 1][n - 1] ) ).multiply( new BigDecimal( det( p , ( n-1 ) ) ) ).doubleValue() ;
    }

    public static void main( String [] args )
    {
        double[][] test = {{1, 2, 3, 4}, {2, 3, 4, 1}, {3, 4, 1, 2}, {4, 1, 2, 3}} ;
        double[][] test_ = {{1, 2, 3}, {2, 3, 4}, {3, 4, 1}} ;
        double[][] testa = {{1, 2}, {3, 4}} ;


        double[][] A = new double[50][50] ;

        for( int i = 0 ; i < A.length ; i++ )
        {
            for( int j = 0 ; j < A.length ; j++ )
            {
                A[i][j] = (int)(Math.random()*1000000000)/10000.0 ;
            }
        }

        long curtime = System.currentTimeMillis() ;
        double det = det( test , test.length ) ;
        System.out.println( det +" ; " + ( System.currentTimeMillis()-curtime ) ) ;
    }

}
