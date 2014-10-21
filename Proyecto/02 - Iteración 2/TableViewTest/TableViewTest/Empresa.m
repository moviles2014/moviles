//
//  Empresa.m
//  TableViewTest
//
//  Created by Camilo Barraza on 10/21/14.
//  Copyright (c) 2014 Camilo Barraza. All rights reserved.
//

#import "Empresa.h"

@implementation Empresa


-(id)initConNombre: (NSString *) nomb
{
    self = [super init];
    
    if(self)
    {
        _nombre = nomb;
    }
    
    return self;
}
@end
