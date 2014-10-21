//
//  Data.m
//  TableViewTest
//
//  Created by Camilo Barraza on 9/1/14.
//  Copyright (c) 2014 Camilo Barraza. All rights reserved.
//

#import "Data.h"

@implementation Data

static NSMutableArray *productos ;
static NSMutableArray *lista ;

+(NSMutableArray*) getAllData {
    if ( productos == nil ){
        productos = [[NSMutableArray alloc] init] ;
    }
    if ( lista == nil ){
        lista = [[NSMutableArray alloc] init] ;
    }
    return productos ;
}

+(void) addName:(NSString *)name{

    return ;
}


+(void) agregarProducto:(Producto *)prod {
    [productos addObject:prod ] ;
    return ;
}

+(NSMutableArray*) getArray {
    return productos ;
}

+(void) addProdLista: (int ) pos {
    [lista addObject:[productos objectAtIndex:pos]]  ;
}

+(NSMutableArray*) getArrayLista {
    return lista ;
}

@end
