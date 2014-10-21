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
static NSMutableArray *parqueaderos ;

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
    if ( parqueaderos == nil ){
        parqueaderos = [[NSMutableArray alloc] init] ;
        
        
       
        Parqueadero *parq = [[Parqueadero alloc] initConNombre:@"testp11" conZona:@"dfa" conHorario:@"fdasfads" conCaracteristicas:@"fdasfda" conDireccion:@"fdasfads" conEmpresa:@"test" conPrecio:34 conCupos:24 conLatitud:43.3 conLongitud:343.3];
        
        [self agregarParqueadero:parq] ;
        
        parq = [[Parqueadero alloc] initConNombre:@"testp12" conZona:@"dfa" conHorario:@"fdasfads" conCaracteristicas:@"fdasfda" conDireccion:@"fdasfads" conEmpresa:@"test" conPrecio:34 conCupos:24 conLatitud:43.3 conLongitud:343.3];
        
        [self agregarParqueadero:parq] ;
        
        parq = [[Parqueadero alloc] initConNombre:@"testp13" conZona:@"dfa" conHorario:@"fdasfads" conCaracteristicas:@"fdasfda" conDireccion:@"fdasfads" conEmpresa:@"test" conPrecio:34 conCupos:24 conLatitud:43.3 conLongitud:343.3];
        
        [self agregarParqueadero:parq] ;
        

        parq = [[Parqueadero alloc] initConNombre:@"testp14" conZona:@"dfa" conHorario:@"fdasfads" conCaracteristicas:@"fdasfda" conDireccion:@"fdasfads" conEmpresa:@"test" conPrecio:34 conCupos:24 conLatitud:43.3 conLongitud:343.3];
        
        [self agregarParqueadero:parq] ;
        
        
    }
    return parqueaderos ;
}

+(NSMutableArray*) getEmpresas {
    if ( productos == nil ){
        productos = [[NSMutableArray alloc] init] ;
    }
    if ( lista == nil ){
        lista = [[NSMutableArray alloc] init] ;
    }
    if ( parqueaderos == nil ){
        parqueaderos = [[NSMutableArray alloc] init] ;
    }
    return parqueaderos ;
}

+(void) addName:(NSString *)name{

    return ;
}

+(void) agregarParqueadero:(Parqueadero *)parq {
    [parqueaderos addObject:parq ] ;
    return ;
}


+(void) agregarProducto:(Producto *)prod {
    [productos addObject:prod ] ;
    NSLog( @"entro colo ")   ;
    return ;
}

+(NSMutableArray*) getArray {
    return parqueaderos ;
}

+(void) addProdLista: (int ) pos {
    [lista addObject:[productos objectAtIndex:pos]]  ;
}

+(NSMutableArray*) getArrayLista {
    return lista ;
}

@end
