//
//  Data.h
//  TableViewTest
//
//  Created by Camilo Barraza on 9/1/14.
//  Copyright (c) 2014 Camilo Barraza. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Parqueadero.h"

@interface Data : NSObject

+(NSMutableArray*) getAllData ;

+(void) agregarParqueadero: (Parqueadero *) parq ;
+(NSMutableArray*) getArray  ;
+(void) addName: (NSString *) namde ;
+(void) addParqLista: (int ) pos ;
+(NSMutableArray*) getArrayLista  ;





@end
