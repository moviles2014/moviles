//
//  Data.m
//  TableViewTest
//
//  Created by Camilo Barraza on 9/1/14.
//  Copyright (c) 2014 Camilo Barraza. All rights reserved.
//

#import "Data.h"
#import "Empresa.h"
#import "Parqueadero.h"

@implementation Data

static NSMutableArray *lista ;
static NSMutableArray *empresas ;

+(NSMutableArray*) getAllData {
    
    if ( lista == nil ){
        lista = [[NSMutableArray alloc] init] ;
    }
    if ( empresas == nil ){
        empresas = [[NSMutableArray alloc] init] ;
        
        NSString* filePath = @"empresas";
        NSString* fileRoot = [[NSBundle mainBundle]
                              pathForResource:filePath ofType:@""];
        // read everything from text
        NSString* fileContents =
        [NSString stringWithContentsOfFile:fileRoot
                                  encoding:NSUTF8StringEncoding error:nil];
        
        // first, separate by new line
        NSArray* allLinedStrings =
        [fileContents componentsSeparatedByCharactersInSet:
         [NSCharacterSet newlineCharacterSet]];
        
        for (int i = 0; i < allLinedStrings.count; i++) {
            NSString* strsInOneLine =
            [allLinedStrings objectAtIndex:i];
            
            
            [self agregarEmpresa:strsInOneLine];
            
        }
        
        filePath = @"parqueaderos";
        fileRoot = [[NSBundle mainBundle]
                              pathForResource:filePath ofType:@""];
        // read everything from text
        fileContents =
        [NSString stringWithContentsOfFile:fileRoot
                                  encoding:NSUTF8StringEncoding error:nil];
        
        // first, separate by new line
        allLinedStrings =
        [fileContents componentsSeparatedByCharactersInSet:
         [NSCharacterSet newlineCharacterSet]];
        
        for (int i = 0; i < allLinedStrings.count; i++) {
            NSString* strsInOneLine =
            [allLinedStrings objectAtIndex:i];
            
            NSArray *info = [strsInOneLine componentsSeparatedByString:@","];
            
            Empresa* act = [self buscarEmpresa:[info objectAtIndex:0]];
            
            Parqueadero * parq = [[Parqueadero alloc] initConNombre:[info objectAtIndex:0] conZona:[info objectAtIndex:2] conHorario:[info objectAtIndex:3] conCaracteristicas:[info objectAtIndex:5] conDireccion:[info objectAtIndex:4] conEmpresa:[act nombre] conPrecio:-1 conCupos:-1 conLatitud:[[info objectAtIndex:6] doubleValue] conLongitud:[[info objectAtIndex:7] doubleValue]];
            [act agregarParqueadero:parq];
            
            
        }
        
        // then break down even further
        
        
        [self sortEmpresas];
        [self sortParqs];
        [self volverListaEmpresas];
    }
    return empresas ;
}

+(void) agregarEmpresa: (NSString *) nomb
{
    Empresa *agregar = [[Empresa alloc] initConNombre: nomb] ;
    [empresas addObject:agregar];


}

+(NSMutableArray *) getEmpresas
{
    return empresas;
}

+(void) sortEmpresas
{
    empresas = [ NSMutableArray arrayWithArray:[empresas sortedArrayUsingSelector:@selector(compare:)]];
}
+(void) volverListaEmpresas
{
    [self cleanLista];

    for (int i = 0; i < [empresas count]; i++) {
        [lista addObject:[[empresas objectAtIndex:i] nombre]];
    }
    
}

+(void) cleanLista
{
    for (int i = 0; i < [lista count]; i++) {
        [lista removeObjectAtIndex:i];
    }
}

+(NSMutableArray *) getLista
{
    return lista;
}

+(Empresa*) buscarEmpresa: (NSString *) nom
{
    for (int i = 0; i < [empresas count]; i++) {
        if ([nom rangeOfString:[[empresas objectAtIndex:i] nombre]].location != NSNotFound) {
            return (Empresa*)[empresas objectAtIndex:i];
        }
    }
    return nil;
}

+(Empresa*) darEmpresaID: (int) elid
{
    return [empresas objectAtIndex:elid];
}

+(void) sortParqs
{
    for (int i = 0; i < [empresas count]; i++) {
        [[empresas objectAtIndex:i] sortParq] ;
    }
}

@end
