package com.ipackage;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;

public class EjmlTest {

    public static void main( String[] args )
    {
        DenseMatrix64F df = new DenseMatrix64F(2,2 ) ;
        df.set( 0 , 0 , 2 ) ;
        df.set( 0 , 1 , 3 ) ;
        df.set( 1 , 0 , 4 ) ;
        df.set( 1 , 1 , 5 ) ;

        System.out.println( df ) ;

        DenseMatrix64F df1 = new DenseMatrix64F(2,2 ) ;
        CommonOps.invert( df , df1 ) ;

        System.out.println( df1 );
    }
}
