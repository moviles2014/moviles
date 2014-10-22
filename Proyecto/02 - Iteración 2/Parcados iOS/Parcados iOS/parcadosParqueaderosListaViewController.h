//
//  parcadosParqueaderosListaViewController.h
//  Parcados iOS
//
//  Created by Fabio Moyano on 10/21/14.
//  Copyright (c) 2014 Fabio Moyano. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface parcadosParqueaderosListaViewController : UITableViewController
@property (nonatomic, copy) NSMutableArray *parqueaderos ;

-(void) setParqueaderos:(NSMutableArray *)parqueaderos ;
@end
