package com.ipackage;

import com.sun.xml.internal.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

public class Test {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    final static String bytetohex( byte b )
    {
        String str = Integer.toHexString( b & 0xff ) ;

        if( str.length() < 2 )
        {
            str = "0" + str ;
        }

        return str ;
    }

    public static void main(String[] args){
//        List<Integer> p = Arrays.asList( 1 ,3 , 4 , 5 ) ;
//        IntSummaryStatistics iss = p.stream().mapToInt( (x)->x ).summaryStatistics() ;
//        System.out.println( iss.getCount() );
//        p.forEach((x)->System.out.println(x));
//        p.forEach(System.out::println);

//        Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));
//
//        System.out.println("OpenCV Mat: " + m.dump());

//        Mat m = Imgcodecs.imread( "/Users/huapingxu/Downloads/gg.tif" , Imgcodecs.IMREAD_UNCHANGED) ;
//
//        int channels = m.channels();
//
//        System.out.println( channels + ";" + m.rows() + ";" + m.cols() );
//
//        double[] pixel = new double[3];
//
//        System.out.println( Arrays.toString( m.get( 0 , 0 ) ) );


//        for (int i = 0, rlen = m.rows(); i < rlen; i++) {
//            for (int j = 0, clen = m.cols(); j < clen; j++) {
//                pixel = m.get(i, j).clone();
//                if (channels == 3) {
//                    pixel[0] = 65535 - pixel[0];//B
//                    pixel[1] = 65535 - pixel[1];//G
//                    pixel[2] = 65535 - pixel[2];//R
//                }
//                System.out.println( Arrays.toString(pixel) );
//            }
//        }

        try
        {

//            byte[] bytes = Files.readAllBytes( Paths.get("/Users/huapingxu/Downloads/cc.dng") );
//
////            Files.write( Paths.get( "/Users/huapingxu/Downloads/cc.txt" ) , bytes ) ;
////            System.out.println(Arrays.toString( byes ) );
////            System.out.println( bytes.length );
//            for( int i = 32803 ; i <= 32805 ; i++ )
//                System.out.println( bytes[i]&0xff );
////            System.out.println( byes[i] >= 0 ? byes[i] : 0xff&byes[i] );
//
//            byte[] tmpb = new byte[1];




//            //IFH
//            System.out.println( bytes[0] +","+ bytes[1] );
//            System.out.println( bytes[2] +","+ bytes[3] );
//            System.out.println( bytes[4] +","+ bytes[5] +","+ bytes[6] +","+ bytes[7] );//IFD
//
//            //IFD
//            System.out.println( bytes[8] +","+ bytes[9] );//DE COUNT
//            //n个DE  2-（n*12+1）            2-649
//            System.out.println( bytes[10] + ",...," + bytes[657] );//DE
//            System.out.println( "-----------------------------" );
//            byte [] tmpbytes = new byte[ 648 ] ;//tmpDE
//            System.arraycopy( bytes , 10 , tmpbytes , 0 , 648 );
//            int index = 0 ;
//            while ( index < tmpbytes.length )
//            {
//                String tag = Test.bytetohex( tmpbytes[index+1] ) + Test.bytetohex( tmpbytes[index] ) ;
//                System.out.println( "tag:" + Integer.parseInt( tag , 16 ) + "(0x" + tag + ")" );//tag
//                int type = Integer.parseInt( Test.bytetohex(tmpbytes[index+3]) + Test.bytetohex(tmpbytes[index+2]) , 16 ) ;
//                System.out.println( "type:" + type );//type
//                System.out.println( "length:" + (tmpbytes[index+4]&0xff) +","+ (tmpbytes[index+5]&0xff) +","+ (tmpbytes[index+6]&0xff) +","+ (tmpbytes[index+7]&0xff) );//length
//
//                String valueOffset  =  "0" ;
//                if( type == 3 || type == 4 )
//                {
//                    valueOffset = Integer.parseInt( Test.bytetohex( tmpbytes[index+11] ) + Test.bytetohex( tmpbytes[index+10] ) + Test.bytetohex( tmpbytes[index+9] ) + Test.bytetohex( tmpbytes[index+8] ) , 16 )+"" ;
//                }
////                else if( type == 2 )
////                {
////
////                }
//                else
//                {
//                    valueOffset = Test.bytetohex( tmpbytes[index+11] ) + Test.bytetohex( tmpbytes[index+10] ) + Test.bytetohex( tmpbytes[index+9] ) + Test.bytetohex( tmpbytes[index+8] ) ;
//                }
//
//                System.out.println( "valueOffset:" + valueOffset );//valueOffset
//                index += 12 ;
//                System.out.println( "-----------------------------"+index );
//            }
//            //next IFD (n*12+2)-(n*12+5)
//            System.out.println( bytes[658] +","+ bytes[659] +","+ bytes[660] +","+ bytes[661] );
//
//            byte[] tmp = { bytes[664] , bytes[665] , bytes[666] , bytes[667] , bytes[668] , bytes[669] , bytes[670] } ;
//            System.out.println( new String( tmp , "ascii" ) ) ;//Make


            System.out.println( (int)(Math.random()*1000000000)/10000.0 );
//            double[][] a = new double[2048][2048] ;
//            double[] b = new double[2048] ;
//
//            for( int i = 0 ; i < a.length ; i++ )
//            {
//                for( int j = 0 ; j < a.length ; j++ )
//                {
//                    a[i][j] = (int)(Math.random()*1000000000)/10000.0 ;
//                }
//
//                b[i] = (int)(Math.random()*1000000000)/10000.0 ;
//            }
//
//            String[] c = new String[2048] ;//e矩阵
//
//            long starttiem = System.currentTimeMillis() ;
//            BigDecimal aa = null;
//            BigDecimal bb = null ;
//            BigDecimal cc = null ;
//            for( int k = 0 ; k < 2048 ; k++ )
//            {
//                String tmpc = "0.0" ;
//                for( int n = 0 ; n < 2048 ; n++ )
//                {
//                    aa = new BigDecimal(a[k][n]) ;
//                    bb = new BigDecimal(b[n]) ;
//                    cc = new BigDecimal(tmpc) ;
//                    tmpc =  cc.add(aa.multiply(bb)).toString() ;
//                }
//                c[k] = tmpc ;
//            }
//
//            System.out.println( System.currentTimeMillis()-starttiem );

        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }

    }
}
