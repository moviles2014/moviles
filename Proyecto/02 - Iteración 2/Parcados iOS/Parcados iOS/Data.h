//
//  Data.h
//  TableViewTest
//
//  Created by Camilo Barraza on 9/1/14.
//  Copyright (c) 2014 Camilo Barraza. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Parqueadero.h"
#import "Empresa.h"

@interface Data : NSObject

+(NSMutableArray*) getAllData ;


+(void)agregarEmpresa: (NSString *) nombre;
+(NSMutableArray *) getEmpresas;
+(NSMutableArray *) getLista;
+(Empresa*) darEmpresaID: (int) elid;

@end
