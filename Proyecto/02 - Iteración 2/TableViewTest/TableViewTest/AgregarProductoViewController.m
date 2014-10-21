//
//  AgregarProductoViewController.m
//  TableViewTest
//
//  Created by FABIO ENRIQUE MOYANO GALKIN on 9/2/14.
//  Copyright (c) 2014 FABIO ENRIQUE MOYANO GALKIN. All rights reserved.
//

#import "AgregarProductoViewController.h"
#import "Producto.h"
#import "Data.h"


@interface AgregarProductoViewController ()

@end

@implementation AgregarProductoViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void) touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    [self.view endEditing:YES];
}



/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/
- (IBAction)AgregarProducto:(id)sender {
 
    
    NSDateFormatter *df = [[NSDateFormatter alloc] init];
    [df setDateFormat:@"dd-MM-yyyy"];
    NSDate *myDate = [df dateFromString: self.txtFecha.text];
    
    NSLog(@"primero %@ " , self.txtPrecio.text) ;
    
    Producto *nuevo = [[Producto alloc] initConNombre:self.txtNombre.text conMarca:self.txtMarca.text conCategoria:self.txtCategoria.text conFecha:myDate conPrecio:[self.txtPrecio.text floatValue]];
    
    NSString *str = [NSString stringWithFormat:@"%f", [nuevo precio]] ;
    NSLog(@"men %@" , str ) ;
    
    
    
    [Data agregarProducto:nuevo] ;
    
    NSLog(@"%@", [[[Data getAllData] objectAtIndex:0 ] nombre   ]);
    
    
    [self dismissViewControllerAnimated:YES completion:nil];
    
}

@end
