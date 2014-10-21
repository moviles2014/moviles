//
//  Data.h
//  TableViewTest
//
//  Created by Camilo Barraza on 9/1/14.
//  Copyright (c) 2014 Camilo Barraza. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Producto.h"

@interface Data : NSObject

+(NSMutableArray*) getAllData ;


+(void) agregarProducto: (Producto *) prod ;
+(NSMutableArray*) getArray  ;
+(void) addName: (NSString *) namde ;


+(void) addProdLista: (int ) pos ;
+(NSMutableArray*) getArrayLista  ;





@end
