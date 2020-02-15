package com.ipackage;

import org.ujmp.core.Matrix;
import org.ujmp.core.bigdecimalmatrix.BigDecimalMatrix;
import org.ujmp.core.util.MathUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class UjmpTest {

    final static int mx = 1000 ;//行
    final static int my = 1000 ;//列
    final static String path = "/Users/huapingxu/Documents/IProjects/doc/c/" ;
    final static String path_ = "/Users/huapingxu/Documents/IProjects/doc/c1.txt" ;

    String [][] matrixa = null ;//T矩阵，光谱筛响应矩阵
    String[] matrixb = null ;//I矩阵，光谱向量
    String[] matrixc = null ;//e矩阵，响应向量

    void matrixdata()//获取矩阵数据
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

        matrixa = new String[mx][my] ;//T矩阵，光谱筛响应矩阵
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
                    matrixa[i][j] = tmpstr[1]  ; //+ MathUtil.nextInteger(100000, 999999)
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

        for( int m = 0 ; m < 2 ; m++ )
            System.out.println( "光谱筛响应矩阵：" + Arrays.toString( matrixa[m] ) );


        //写入文件
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get( "/Users/huapingxu/Downloads/data_.txt" )))
        {
            for( int k = 0 ; k < mx ; k++ )
            {
                for ( int l = 0 ; l < my ; l++ )
                {
                    writer.write( matrixa[k][l] );

                    if( l != my -1 )
                    {
                        writer.write( "," );
                    }
                }
                writer.write( "\r\n" );
            }
        }
        catch ( Exception ex ){ ex.printStackTrace();}


        matrixb = new String[mx] ;//I矩阵，光谱向量
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

        System.out.println( "光谱向量:" + Arrays.toString( matrixb ) );

        //写入文件
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get( "/Users/huapingxu/Downloads/bb.txt" )))
        {
            for ( String str : matrixb )
                writer.write( str+"\r\n" );
        }
        catch ( Exception ex ){ ex.printStackTrace();}

        matrixc = new String[mx] ;//e矩阵，响应向量

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

        //写入文件
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get( "/Users/huapingxu/Downloads/data_c.txt" )))
        {
            for( int p = 0 ; p < mx ; p++ )
            {
                writer.write( matrixc[p] +"\r\n" );
            }
        }
        catch ( Exception ex ){ ex.printStackTrace();}

        System.out.println( "响应向量：" + Arrays.toString( matrixc ) );
    }

    Matrix matrixEdata()//获取响应向量矩阵
    {
        Matrix mt  = Matrix.Factory.zeros( mx , 1 ) ;

        for( int i = 0 ; i < mt.getRowCount() ; i++ )
        {
            mt.setAsBigDecimal( new BigDecimal(matrixc[ i ]) , i , 0 ); ;
        }

//        System.out.println( "响应向量->" + mt.getAsDouble( 0 , 0 ) +", "+ mt.getAsDouble( 1 , 0 ) ) ;

        return mt ;
    }

    Matrix matrixInvdata()//获取光谱筛响应矩阵的逆矩阵
    {
        Matrix mt  = Matrix.Factory.zeros( mx, my ) ;

        for( int i = 0 ; i < mt.getRowCount() ; i++ )
        {
            for( int j = 0 ; j < mt.getColumnCount() ; j++ )
            {
                mt.setAsBigDecimal( new BigDecimal(matrixa[i][j]) , i , j ) ;
//                mt.setAsDouble( Double.valueOf(matrixa[i][j]) , i , j ); ;
            }
        }

        System.out.println( "光谱筛响应矩阵->" ) ;
        System.out.println( mt.getAsDouble( 0 , 0 ) +", "+ mt.getAsDouble( 0 , 1 ) +", "+ mt.getAsDouble( 0 , 2 )) ;

//        long s = System.currentTimeMillis() ;
        Matrix mt1 = mt.inv() ;
//        System.out.println( System.currentTimeMillis() - s );

        System.out.println( "光谱筛响应矩阵逆矩阵->" ) ;
        System.out.println( mt1.getAsBigDecimal( 0 , 0 ) +", "+ mt1.getAsBigDecimal( 0 , 1 ) +", "+ mt1.getAsDouble( 0 , 2 ) ) ;

        return mt1 ;
    }



    public static void main(String[] args) {

//        Matrix mt = Matrix.Factory.zeros( 3 , 3 ) ;
//        mt.setAsDouble( 2345.2456 , 0 , 0 ) ;
//        mt.setAsDouble( 4534.8736 , 0 , 1 ) ;
//        mt.setAsDouble( 1308.2093 , 0 , 2 ) ;
//        mt.setAsDouble( 5094.3853 , 1 , 0 ) ;
//        mt.setAsDouble( 3890.4682 , 1 , 1 ) ;
//        mt.setAsDouble( 8194.2978 , 1 , 2 ) ;
//        mt.setAsDouble( 3902.4831 , 2 , 0 ) ;
//        mt.setAsDouble( 7903.2053 , 2 , 1 ) ;
//        mt.setAsDouble( 9997.8743 , 2 , 2 ) ;


        UjmpTest ut = new UjmpTest() ;

        ut.matrixdata() ;//处理数据
        Matrix m = ut.matrixEdata() ;//获取响应向量
        System.out.println( "" ) ;
        double s = System.currentTimeMillis() ;
        Matrix m1 = ut.matrixInvdata() ;//获取逆矩阵

        Matrix m2 = m1.toBigDecimalMatrix().mtimes(m.toBigDecimalMatrix()) ;//计算光谱向量
        System.out.println( "计算时间：" + (System.currentTimeMillis() - s) );
        System.out.print( "光谱向量->" ) ;

        for( int i = 0 ; i < m2.getRowCount() ; i++ )
            System.out.print( m2.getAsBigDecimal( i , 0 )+", " ) ;

        //写入文件
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get( "/Users/huapingxu/Downloads/bb_.txt" )))
        {
            for( int i = 0 ; i < m2.getRowCount() ; i++ )
            {
                BigDecimal bgc = m2.getAsBigDecimal( i , 0 ) ;
                writer.write( bgc+"\r\n" );
                System.out.print( bgc+", " ) ;
            }
        }
        catch ( Exception ex ){ ex.printStackTrace();}

    }
}
