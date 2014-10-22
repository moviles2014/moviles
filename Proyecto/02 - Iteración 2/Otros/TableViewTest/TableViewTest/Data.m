//
//  Data.m
//  TableViewTest
//
//  Created by Camilo Barraza on 9/1/14.
//  Copyright (c) 2014 Camilo Barraza. All rights reserved.
//

#import "Data.h"

@implementation Data

static NSMutableArray *lista ;
static NSMutableArray *parqueaderos ;

+(NSMutableArray*) getAllData {
    
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


+(NSMutableArray*) getArray {
    return parqueaderos ;
}

+(void) addParqLista: (int ) pos {
    [lista addObject:[parqueaderos objectAtIndex:pos]]  ;
}

+(NSMutableArray*) getArrayLista {
    return lista ;
}

@end
