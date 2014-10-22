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

@property (nonatomic, copy) NSString * cupos;
/**
 * Precio del parqueadero
 */
@property (nonatomic, copy) NSString * precio;
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


@property (nonatomic, copy) NSString * latitud ;

@property (nonatomic, copy) NSString * longitud ;



-(id)initConNombre: (NSString *) nomb
          conZona: (NSString *) zona
      conHorario: (NSString *) hor
          conCaracteristicas: (NSString *) car
         conDireccion: (NSString *) direccion
        conEmpresa: (NSString *) empresa
         conPrecio: (NSString *)  prec
          conCupos: (NSString *)  cupos
        conLatitud: (NSString *)  latitud
       conLongitud: (NSString *)  longitud;

@end
