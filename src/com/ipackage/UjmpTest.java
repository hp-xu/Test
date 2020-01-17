package com.ipackage;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;

public class UjmpTest {

    public static void main(String[] args) {

        Matrix mt = DenseMatrix.Factory.zeros( 3 , 3 ) ;
        mt.setAsDouble( 2345.2456 , 0 , 0 ) ;
        mt.setAsDouble( 4534.8736 , 0 , 1 ) ;
        mt.setAsDouble( 1308.2093 , 0 , 2 ) ;
        mt.setAsDouble( 5094.3853 , 1 , 0 ) ;
        mt.setAsDouble( 3890.4682 , 1 , 1 ) ;
        mt.setAsDouble( 8194.2978 , 1 , 2 ) ;
        mt.setAsDouble( 3902.4831 , 2 , 0 ) ;
        mt.setAsDouble( 7903.2053 , 2 , 1 ) ;
        mt.setAsDouble( 9997.8743 , 2 , 2 ) ;

//        Matrix mt  = DenseMatrix.Factory.rand( 10, 10 ) ;

        System.out.println( mt ) ;
        System.out.println( mt.inv() ) ;
    }
}
