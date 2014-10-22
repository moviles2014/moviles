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

static NSString* newMessage ;

+(void) setNewMessage: (NSString *) newMsg  {
    newMessage = newMsg ;
}

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
        
        
       
        Parqueadero *parq = [[Parqueadero alloc] initConNombre:@"Aparcar Carrera 61" conZona:@"test" conHorario:@"L-V 9:00 a 5:00pm" conCaracteristicas:@"Descubierto" conDireccion:@"Carrera 61 No. 19-88" conEmpresa:@"Aparcar" conPrecio:@"no hay informacion disponible"  conCupos:@"no hay informacion disponible" conLatitud:@"34.1" conLongitud:@"34.2"];
        
        [self agregarParqueadero:parq] ;
        
        parq = [[Parqueadero alloc] initConNombre:@"IPark Calle 70" conZona:@"dfa" conHorario:@"L-D 8:00am a 8:00pm" conCaracteristicas:@"Descubierto" conDireccion:@"Calle 70 No.79-73" conEmpresa:@"IPark" conPrecio:@"no hay informacion disponible" conCupos:@"no hay informacion disponible" conLatitud:@"34" conLongitud:@"34"];
        
        [self agregarParqueadero:parq] ;
        
        parq = [[Parqueadero alloc] initConNombre:@"testp13" conZona:@"dfa" conHorario:@"fdasfads" conCaracteristicas:@"fdasfda" conDireccion:@"calle 4 " conEmpresa:@"test" conPrecio:@"34" conCupos:@"34" conLatitud:@"34" conLongitud:@"34"];
        
        [self agregarParqueadero:parq] ;
        

        parq = [[Parqueadero alloc] initConNombre:@"testp14" conZona:@"dfa" conHorario:@"fdasfads" conCaracteristicas:@"fdasfda" conDireccion:@"por ahi" conEmpresa:@"test" conPrecio:@"34" conCupos:@"34" conLatitud:@"34" conLongitud:@"34"];
        
        [self agregarParqueadero:parq] ;
        
        
    }
    return parqueaderos ;
}

+(NSString*) getMessage {
    return newMessage ;
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
