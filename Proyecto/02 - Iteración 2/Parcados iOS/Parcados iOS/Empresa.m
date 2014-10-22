//
//  Empresa.m
//  TableViewTest
//
//  Created by Camilo Barraza on 10/21/14.
//  Copyright (c) 2014 Camilo Barraza. All rights reserved.
//

#import "Empresa.h"
#import "Parqueadero.h"

@implementation Empresa


-(id)initConNombre: (NSString *) nomb
{
    self = [super init];
    
    if(self)
    {
        _parqueaderos = [[NSMutableArray alloc] init] ;
        _nombre = nomb;
    }
    
    return self;
}

-(void) agregarParqueadero:(Parqueadero *)parq {
    [_parqueaderos addObject:parq ] ;
    return ;
}


-(NSMutableArray*) getArrayParqueaderos {
    return _parqueaderos ;
}

-(void) sortParq
{
    _parqueaderos = [ NSMutableArray arrayWithArray:[_parqueaderos sortedArrayUsingSelector:@selector(compare:)]];
}


- (NSComparisonResult)compare:(Empresa *)otherObject {
    return [self.nombre compare:otherObject.nombre];
}

@end
