//
//  Producto.h
//  TableViewTest
//
//  Created by FABIO ENRIQUE MOYANO GALKIN on 9/2/14.
//  Copyright (c) 2014 FABIO ENRIQUE MOYANO GALKIN. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Producto : NSObject

@property (nonatomic, copy) NSString * nombre;
@property (nonatomic, copy) NSString * marca;
@property (nonatomic, copy) NSString * categoria;
@property (nonatomic, copy) NSDate * fecha;
@property float precio;

-(id)initConNombre: (NSString *) nomb
          conMarca: (NSString *) marc
      conCategoria: (NSString *) cate
          conFecha: (NSDate *) fech
         conPrecio: (float) prec;

@end
