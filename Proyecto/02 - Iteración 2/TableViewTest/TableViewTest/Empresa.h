//
//  Empresa.h
//  TableViewTest
//
//  Created by Camilo Barraza on 10/21/14.
//  Copyright (c) 2014 Camilo Barraza. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Empresa : NSObject

@property (nonatomic, copy) NSString * nombre;
@property (nonatomic, copy) NSMutableArray *parqueaderos ;


-(id)initConNombre: (NSString *) nomb ; 

@end
