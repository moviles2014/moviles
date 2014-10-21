//
//  Parqueadero.m
//  TableViewTest
//
//  Created by Camilo Barraza on 10/21/14.
//  Copyright (c) 2014 Camilo Barraza. All rights reserved.
//

#import "Parqueadero.h"

@implementation Parqueadero

-(id)initConNombre: (NSString *) nomb
           conZona: (NSString *) zona
        conHorario: (NSString *) hor
conCaracteristicas: (NSString *) car
      conDireccion: (NSString *) direccion
        conEmpresa: (NSString *) empresa
         conPrecio: (NSString *) prec
          conCupos: (NSString *) cup
        conLatitud: (NSString *) latitud
       conLongitud: (NSString *) longitud {
    
    
    self = [super init];
    
    if(self)
    {
        _nombre = nomb;
        _zona = zona ;
        _empresa = empresa ;
        _horario = hor ;
        _caracteristicas = car ;
        _direccion = direccion ;
        _precio = prec ;
        _cupos = cup ;
        _latitud = latitud ;
        _longitud = longitud ;
    }
    
    return self;
    
}



@end
