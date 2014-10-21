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
static NSMutableArray *empresas ;

+(NSMutableArray*) getAllData {
    if ( productos == nil ){
        productos = [[NSMutableArray alloc] init] ;
    }
    if ( lista == nil ){
        lista = [[NSMutableArray alloc] init] ;
    }
    if ( empresas == nil ){
        empresas = [[NSMutableArray alloc] init] ;
    }
    return productos ;
}

+(NSMutableArray*) getEmpresas {
    if ( productos == nil ){
        productos = [[NSMutableArray alloc] init] ;
    }
    if ( lista == nil ){
        lista = [[NSMutableArray alloc] init] ;
    }
    if ( empresas == nil ){
        empresas = [[NSMutableArray alloc] init] ;
    }
    return empresas ;
}

+(void) addName:(NSString *)name{

    return ;
}

+(void) agregarEmpresa:(Empresa *) emp {
    [empresas addObject:emp ] ;
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
