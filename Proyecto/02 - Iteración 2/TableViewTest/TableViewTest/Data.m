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
        
        Producto *nuevo = [[Producto alloc] initConNombre:@"test1" conMarca:@"algo" conCategoria:@"categoria" conFecha:[NSDate date] conPrecio:23.2f ];
        [self agregarProducto:nuevo] ;
        
        nuevo = [[Producto alloc] initConNombre:@"test2" conMarca:@"algo" conCategoria:@"categoria" conFecha:[NSDate date] conPrecio:23.2f ];
        [self agregarProducto:nuevo] ;
        
        nuevo = [[Producto alloc] initConNombre:@"test3" conMarca:@"algo" conCategoria:@"categoria" conFecha:[NSDate date] conPrecio:23.2f ];
        [self agregarProducto:nuevo] ;
    }
    
    if ( lista == nil ){
        lista = [[NSMutableArray alloc] init] ;
    }
    if ( empresas == nil ){
        empresas = [[NSMutableArray alloc] init] ;
        
        
        Empresa *emp = [[Empresa alloc] initConNombre:@"teste1" ] ;
        Parqueadero *parq = [[Parqueadero alloc] initConNombre:@"testp11" conZona:@"dfa" conHorario:@"fdasfads" conCaracteristicas:@"fdasfda" conDireccion:@"fdasfads" conPrecio:34 conCupos:24 conLatitud:43.3 conLongitud:343.3];
        [emp agregarParqueadero:parq]  ;

        parq = [[Parqueadero alloc] initConNombre:@"testp12" conZona:@"dfa" conHorario:@"fdasfads" conCaracteristicas:@"fdasfda" conDireccion:@"fdasfads" conPrecio:34 conCupos:24 conLatitud:43.3 conLongitud:343.3];
        [emp agregarParqueadero:parq]  ;
        
        [self agregarEmpresa:emp] ;
        
        
        
        emp = [[Empresa alloc] initConNombre:@"teste2" ] ;
        parq = [[Parqueadero alloc] initConNombre:@"testp21" conZona:@"dfa" conHorario:@"fdasfads" conCaracteristicas:@"fdasfda" conDireccion:@"fdasfads" conPrecio:34 conCupos:24 conLatitud:43.3 conLongitud:343.3];
        [emp agregarParqueadero:parq]  ;
        [self agregarEmpresa:emp] ;

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
    NSLog( @"entro colo ")   ;
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
