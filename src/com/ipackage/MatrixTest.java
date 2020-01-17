package com.ipackage;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class MatrixTest {

    final static int mx = 1001 ;//行
    final static int my = 1001 ;//列
    final static int mu = 10 ; //精度位数
    final static String path = "/Users/huapingxu/Documents/IProjects/doc/c/" ;
    final static String path_ = "/Users/huapingxu/Documents/IProjects/doc/c1.txt" ;

    String[][] matrixdata()//获取矩阵数据
    {
        List<String> list = new ArrayList<String>();

        try
        {
            Stream<Path> stream = Files.list( Paths.get( path ) ) ;
            Iterator<Path> iterable = stream.iterator() ;
            while ( iterable.hasNext() )
            {
                list.add( iterable.next().getFileName().toString() ) ;
            }

//            System.out.println( list.toString() );

            list.sort( Comparator.comparing(String::toString) );

//            System.out.println( list.toString() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        String [][] matrixa = new String[mx][my] ;//T矩阵
        int i = 0 ;
        int j = 0 ;
        int ix = 0 ;

        for( String filename : list)
        {

            if( ix == mx )
            {
                break;
            }

            try(BufferedReader ina = Files.newBufferedReader( Paths.get( path + filename ));)
            {
//                System.out.println( "["+filename+"]");
                j = 0 ;
                String str = "" ;
                int iy = 0 ;
                while ( iy < mx )
                {
                    String[] tmpstr = ina.readLine().split( "\t" ) ;
                    matrixa[i][j] = tmpstr[1] ;
                    j++ ;
                    iy++ ;
                }

//                System.out.println( "" );
            }
            catch ( Exception e1 )
            {
                e1.printStackTrace();
            }

            i++ ;
            ix++ ;
        }

//        for( int m = 0 ; m < mx ; m++ )
//            System.out.println( Arrays.toString( matrixa[m] ) );

        String[] matrixb = new String[mx] ;//I矩阵
        try(BufferedReader inb = Files.newBufferedReader( Paths.get( path_ ) ) ;)
        {
            String str = "" ;
            i = 0 ;
            int iy = 0 ;
            while ( iy < mx )//410
            {
                String[] tmpstr = inb.readLine().split( "\t" ) ;
                matrixb[i] = tmpstr[1] ;
                i++ ;
                iy++ ;
            }
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
        }

//        System.out.println( Arrays.toString( matrixb ) );


        String[] matrixc = new String[mx] ;//e矩阵

        BigDecimal aa = null;
        BigDecimal bb = null ;
        BigDecimal cc = null ;
        for( int k = 0 ; k < mx ; k++ )
        {
            String tmpc = "0.0" ;
            for( int n = 0 ; n < my ; n++ )
            {
                aa = new BigDecimal(matrixa[k][n]) ;
                bb = new BigDecimal(matrixb[n]) ;
                cc = new BigDecimal(tmpc) ;
                tmpc =  cc.add(aa.multiply(bb)).toString() ;
            }
            matrixc[k] = tmpc ;
        }

//        System.out.println( Arrays.toString( matrixc ) );



        String[][] matrixab = new String[mx][my+1] ;//增广矩阵

        for( int t = 0 ; t < mx ; t++ )
        {
            for ( int r = 0 ; r < my ; r++ )
            {
                matrixab[t][r] = matrixa[t][r] ;
            }

            matrixab[t][my] = matrixc[t] ;
        }

        return matrixab ;
    }

    String[][] matrixmaindo( String[][] matrixab , int n )//选主元
    {
        int index = n ;

        for( int i = n+1 ; i < mx ; i++ )
        {
            if( ((new BigDecimal( matrixab[i][n] )).abs()).subtract( (new BigDecimal( matrixab[i-1][n] )).abs() ).doubleValue() > 0 )
            {
                index = i ;
            }
        }

        if( index != n )
        {
            String tmpstr = matrixab[n][n] ;

            for( int j = n ; j <= my ; j++ )
            {
                tmpstr = matrixab[index][j] ;
                matrixab[index][j] = matrixab[n][j] ;
                matrixab[n][j] = tmpstr ;
            }
        }

//        for( int j = 0 ; j < matrixab.length ; j++ )
//            System.out.println( Arrays.toString( matrixab[j]));
//        System.out.println( "-交换-------------------"+n );

        return matrixab ;
    }

    String[][] matrixdo( String[][] matrixab , int n )//消元
    {
        try
        {
            BigDecimal b1 = null ;
            BigDecimal b2 = null ;
            BigDecimal tmpnum = null ;//系数
            for( int x = n ; x < mx-1 ; x++ )
            {
                tmpnum = new BigDecimal("1") ;
                for( int l = n ; l < my+1 ; l++ )
                {
                    b1 = new BigDecimal(matrixab[x+1][l]);
                    b2 = new BigDecimal(matrixab[n][l]);

                    if( l == n )
                    {
                        tmpnum = b1.divide(b2, mu, BigDecimal.ROUND_UP) ;//除,保留位数
                    }

                    matrixab[x+1][l] = b1.subtract( b2.multiply(tmpnum).divide( new BigDecimal("1") ,mu , BigDecimal.ROUND_HALF_UP) ).toString() ;//乘->减,保留位数
                }
            }
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            System.exit( 0 );
        }

        return matrixab ;
    }

    String[][] matrixsu( String[][] matrixab , int n )//回带求解
    {
        for( int i = n ; i >=0 ; i-- )
        {
            for( int j = i +1 ; j<= n ; j++ )
                matrixab[i][n+1] = (new BigDecimal(matrixab[i][n+1])).subtract( (new BigDecimal(matrixab[i][j])).multiply(new BigDecimal(matrixab[j][n+1])) ).toString() ;
            matrixab[i][n+1]=(new BigDecimal(matrixab[i][n+1])).divide( (new BigDecimal(matrixab[i][i])) , mu, BigDecimal.ROUND_HALF_UP).toString() ;
        }

        return matrixab ;
    }

//    public static void main( String[] args )
//    {
//        String[][] aa = { {"5" ,"2" ,"3"} , {"3", "4" ,"5"} , {"4", "1" ,"6"} } ;
//        for( int i = 0 ; i < aa.length ;i++ )
//            System.out.println( Arrays.toString( aa[i]));
//
//        System.out.println( "-------------------" );
//        MatrixTest mt = new MatrixTest() ;
//        String[][] bb = mt.matrixmaindo( aa , 0 ) ;
//        for( int j = 0 ; j < bb.length ; j++ )
//            System.out.println( Arrays.toString( bb[j]));
//
//        System.out.println( "-------------------" );
//        String[][] cc = mt.matrixmaindo( bb , 1 ) ;
//        for( int m = 0 ; m < cc.length ; m++ )
//            System.out.println( Arrays.toString( cc[m]));
//    }

    public static void main( String[] args )
    {
        MatrixTest mt = new MatrixTest() ;
        String[][] matrixab = mt.matrixdata() ;

        System.out.println( "高斯消元前：" );
        for( int p = 0 ; p < mx ; p++ )
            System.out.println( Arrays.toString( matrixab[p] ) );
        System.out.println( "- - - - - - - - " );

        System.out.println( "高斯消元中：" );

        String[][] matrixab_ = matrixab ;
        for( int n = 0 ; n < mx-1 ; n++ )
        {
            matrixab_ = mt.matrixmaindo( matrixab_ , n ) ;//选主元
//            for( int p = 0 ; p < mx ; p++ )
//                System.out.println( Arrays.toString( matrixab_[p] ) );

            matrixab_ = mt.matrixdo( matrixab_ , n ) ;//消元
//            for( int p = 0 ; p < mx ; p++ )
//                System.out.println( Arrays.toString( matrixab_[p] ) );

            System.out.print( "->" + (n+1) );
        }

        System.out.println( "->end- - -" );
        System.out.println( "高斯消元后：" );
        for( int p = 0 ; p < mx ; p++ )
            System.out.println( Arrays.toString( matrixab_[p] ) );

        System.out.println( "回带求解后：" );
        matrixab_ = mt.matrixsu( matrixab_ , mx-1 ) ;
        int mm = matrixab_[0].length-1 ;
        for ( int p = 0 ; p< matrixab_.length ;p++ )
            System.out.println( matrixab_[p][mm] );

    }
}