//
//  AgregarProductoViewController.h
//  TableViewTest
//
//  Created by FABIO ENRIQUE MOYANO GALKIN on 9/2/14.
//  Copyright (c) 2014 FABIO ENRIQUE MOYANO GALKIN. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AgregarProductoViewController : UIViewController
@property (weak, nonatomic) IBOutlet UITextField *txtNombre;
@property (weak, nonatomic) IBOutlet UITextField *txtMarca;
@property (weak, nonatomic) IBOutlet UITextField *txtPrecio;
@property (weak, nonatomic) IBOutlet UITextField *txtCategoria;
@property (weak, nonatomic) IBOutlet UITextField *txtFecha;


@end
