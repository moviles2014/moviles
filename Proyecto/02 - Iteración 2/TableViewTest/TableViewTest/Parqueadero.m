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
         conPrecio: (int) prec
          conCupos: (int) cupos
        conLatitud: (double) latitud
       conLongitud: (double) longitud {
    
    
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
        _cupos = cupos ;
        _latitud = latitud ;
        _longitud = longitud ;
    }
    
    return self;
    
}



@end
