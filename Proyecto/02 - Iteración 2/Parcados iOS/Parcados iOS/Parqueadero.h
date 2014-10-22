//
//  Parqueadero.h
//  TableViewTest
//
//  Created by Camilo Barraza on 10/21/14.
//  Copyright (c) 2014 Camilo Barraza. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Parqueadero : NSObject
/**
 * Cupos del parqueadero
 */

@property (assign) int cupos;
/**
 * Precio del parqueadero
 */
@property (assign) int precio;
/**
 * Horario del parqueadero
 */
@property (nonatomic, copy) NSString * horario ;

@property (nonatomic, copy) NSString * empresa ;

/**
 * Características del parqueadero
 */
@property (nonatomic, copy) NSString * caracteristicas ;

/**
 * Dirección del parqueadero
 */
@property (nonatomic, copy) NSString * direccion ;

/**
 * Nombre del parqueadero
 */
@property (nonatomic, copy) NSString * nombre ;

/**
 * Nombre de la zona
 */
@property (nonatomic, copy) NSString * zona ;

@property (assign) double latitud;
@property (assign) double longitud;


-(id)initConNombre: (NSString *) nomb
          conZona: (NSString *) zona
      conHorario: (NSString *) hor
          conCaracteristicas: (NSString *) car
         conDireccion: (NSString *) direccion
        conEmpresa: (NSString *) empresa
         conPrecio: (int) prec
          conCupos: (int) cupos
        conLatitud: (double) latitud
       conLongitud: (double) longitud;
@end
