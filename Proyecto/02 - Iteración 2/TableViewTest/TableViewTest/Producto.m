//
//  Producto.m
//  TableViewTest
//
//  Created by FABIO ENRIQUE MOYANO GALKIN on 9/2/14.
//  Copyright (c) 2014 FABIO ENRIQUE MOYANO GALKIN. All rights reserved.
//

#import "Producto.h"

@implementation Producto
@synthesize nombre, marca, categoria, fecha, precio;

-(id)initConNombre: (NSString *) nomb
conMarca: (NSString *) marc
conCategoria: (NSString *) cate
conFecha: (NSDate *) fech
conPrecio: (float) prec
{
    self = [super init];
    
    if(self)
    {
        nombre = nomb;
        marca = marc;
        categoria = cate;
        fecha = fech;
        precio = prec;
    }
    
    return self;
}


@end
