package com.ipackage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class DNGTest {

    private static byte[] bytes = null ;

    final static String bytetohex( byte b )
    {
        String str = Integer.toHexString( b & 0xff ) ;

        if( str.length() < 2 )
        {
            str = "0" + str ;
        }

        return str ;
    }

    final static HashMap<String,String[]> getTAGmap()
    {
        HashMap< String , String[]> tagmap = null ;

        try
        {
            bytes = Files.readAllBytes( Paths.get("/Users/huapingxu/Downloads/cc.dng") );

            //IFH
            System.out.println( bytes[0] +","+ bytes[1] );
            System.out.println( Integer.parseInt(DNGTest.bytetohex(bytes[3]) + DNGTest.bytetohex(bytes[2]) , 16) );
            int ifdoffset = Integer.parseInt(DNGTest.bytetohex(bytes[7]) +DNGTest.bytetohex(bytes[6]) +DNGTest.bytetohex(bytes[5]) + DNGTest.bytetohex(bytes[4]) , 16 );
            System.out.println( ifdoffset );//IFD

            //IFD
            int DECount = Integer.parseInt( DNGTest.bytetohex( bytes[ifdoffset+1]) + DNGTest.bytetohex( bytes[ifdoffset] ), 16 ) ;
            System.out.println( "DECount = " + DECount );//DE COUNT
            //n个DE  2-（n*12+1）            2-649
            byte [] tmpbytes = new byte[ DECount*12+4 ] ;//tmpDE
            System.arraycopy( bytes , ifdoffset+2 , tmpbytes , 0 , DECount*12+4 );
            int index = 0 ;
            String [] tmpstr = null ;
            tagmap = new HashMap<>( DECount ) ;
            while ( index < DECount*12 )
            {
                tmpstr = new String[4] ;
                tmpstr[0] = Integer.parseInt(DNGTest.bytetohex( tmpbytes[index+1] ) + DNGTest.bytetohex( tmpbytes[index] ) , 16 ) + "" ;
                tmpstr[1] = Integer.parseInt(DNGTest.bytetohex( tmpbytes[index+3] ) + DNGTest.bytetohex( tmpbytes[index+2] ) , 16 ) + "" ;
                tmpstr[2] = Integer.parseInt(DNGTest.bytetohex( tmpbytes[index+7] ) + DNGTest.bytetohex( tmpbytes[index+6] ) + DNGTest.bytetohex(tmpbytes[index+5]) + DNGTest.bytetohex(tmpbytes[index+4]), 16) + "" ;
                tmpstr[3] = DNGTest.bytetohex( tmpbytes[index+11] ) + DNGTest.bytetohex( tmpbytes[index+10] ) + DNGTest.bytetohex( tmpbytes[index+9] ) + DNGTest.bytetohex( tmpbytes[index+8] ) ;

                tagmap.put( tmpstr[0] , tmpstr ) ;
                index += 12 ;
            }
            //next IFD (n*12+2)-(n*12+5)
            System.out.println( "Next IFD = " + tmpbytes[index+3] +","+ tmpbytes[index+2] +","+ tmpbytes[index+1] +","+ tmpbytes[index] );
        }
        catch (Exception ex){ex.printStackTrace();}

        return tagmap ;
    }

    final static String[][] getImgData( HashMap<String,String[]> tagmap )
    {
        int type = 0 ;
        int length = 0 ;
        int valueoffset = 0 ;

        int imagewidth = Integer.parseInt( tagmap.get( "256" )[3] , 16 ) ;
        System.out.println( "imagewidth = " + imagewidth ) ;

        int imagelength = Integer.parseInt( tagmap.get( "257" )[3] , 16 ) ;
        System.out.println( "imagelength = " + imagelength ) ;

        String bitsPerSample = Integer.parseInt( tagmap.get( "258" )[3] , 16 ) + "" ;
        System.out.println( "bitsPerSample = " + bitsPerSample ) ;

        type = Integer.parseInt(tagmap.get( "273" )[1]) ;//
        length = Integer.parseInt(tagmap.get( "273" )[2]) ;
        valueoffset = Integer.parseInt( tagmap.get( "273" )[3] , 16 ) ;
        System.out.println( "stripOffsets = " + type + "," + length + "," + valueoffset ) ;
        Integer[] stripOffsets = new Integer[length] ;
        for( int i = 0 ; i < length ; i++ )
        {
            stripOffsets[ i ] = Integer.parseInt(DNGTest.bytetohex( bytes[valueoffset+3] ) + DNGTest.bytetohex( bytes[valueoffset+2] ) + DNGTest.bytetohex( bytes[valueoffset+1] ) + DNGTest.bytetohex( bytes[valueoffset] ) , 16 ) ;
            valueoffset += 4 ;
        }
        System.out.println( Arrays.toString( stripOffsets ) );


        String rowsPerStrip = Integer.parseInt( tagmap.get( "278" )[3] , 16 ) + "" ;
        System.out.println( "rowsPerStrip = " + rowsPerStrip ) ;

        type = Integer.parseInt(tagmap.get( "279" )[1]) ;//
        length = Integer.parseInt(tagmap.get( "279" )[2]) ;
        valueoffset = Integer.parseInt( tagmap.get( "279" )[3] , 16 ) ;
        System.out.println( "stripByteCounts = " + type + "," + length + "," + valueoffset ) ;
        Integer[] stripByteCounts = new Integer[length] ;
        for( int i = 0 ; i < length ; i++ )
        {
            stripByteCounts[ i ] = Integer.parseInt(DNGTest.bytetohex( bytes[valueoffset+3] ) + DNGTest.bytetohex( bytes[valueoffset+2] ) + DNGTest.bytetohex( bytes[valueoffset+1] ) + DNGTest.bytetohex( bytes[valueoffset] ) , 16 ) ;
            valueoffset += 4 ;
        }
        System.out.println( Arrays.toString( stripByteCounts ) );

        String[][] imgArr = new String[imagelength][imagewidth] ;//3968*2976
        for( int j = 0 ; j < stripOffsets.length ; j++ )//2976
        {
            int idx = stripOffsets[j] ;
            for( int k = 0 ; k < stripByteCounts[j]/2 ; k++ )//7936/2
            {
                imgArr[j][k] = DNGTest.bytetohex( bytes[idx+1] ) + DNGTest.bytetohex( bytes[idx] ) ;
                idx += 2 ;
            }
        }

//        imgArr[0][0] = Integer.parseInt(DNGTest.bytetohex( bytes[stripOffsets[0]+1] ) + DNGTest.bytetohex( bytes[stripOffsets[0]] ) , 16 ) ;

        return imgArr ;
    }

    public static void main( String[] args )
    {
        String[][] imgArr = DNGTest.getImgData( DNGTest.getTAGmap() ) ;

        System.out.println( "ImgData:" );
        for( int i = 0 ; i < 2 ; i++ )
        {
            System.out.println( Arrays.toString( imgArr[ i ] ) ) ;
        }


//            byte[] tmp = { bytes[664] , bytes[665] , bytes[666] , bytes[667] , bytes[668] , bytes[669] , bytes[670] } ;
//            System.out.println( new String( tmp , "ascii" ) ) ;//Make
//
//            System.out.println( DNGTest.stripOffsets ) ;
//            System.out.println( Integer.parseInt(DNGTest.bytetohex( bytes[685] ) + DNGTest.bytetohex( bytes[684] ) , 16 ) );
//            System.out.println( Integer.parseInt(DNGTest.bytetohex( bytes[25825] ) + DNGTest.bytetohex( bytes[25824] ) , 16 ) + "("+DNGTest.bytetohex( bytes[25825] ) + DNGTest.bytetohex( bytes[25824])+")" );

    }

}
