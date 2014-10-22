//
//  DetallesViewController.m
//  TableViewTest
//
//  Created by Camilo Barraza on 9/1/14.
//  Copyright (c) 2014 Camilo Barraza. All rights reserved.
//

#import "DetallesViewController.h"
#import "Data.h"

@interface DetallesViewController ()

@end

@implementation DetallesViewController

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
    self.nombre.text = self.DetailModal[0]  ;
    self.categoria.text = self.DetailModal[1]  ;
    self.marca.text = self.DetailModal[2]  ;
    self.precio.text = self.DetailModal[3]  ;
    self.fecha.text = self.DetailModal[4]  ;
    self.title1.text = self.DetailModal[5] ;
    NSLog (@"pilla coletieri  %@" , self.DetailModal[3]  ) ;

}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)actualizar:(id)sender {
    NSString *msg = [Data getMessage] ;
    if ([ msg compare:@"no hay na"] != 0 ){
        NSArray *listItems = [msg componentsSeparatedByString:@";"];
        NSLog(@"debugeando") ;
        for (id object in listItems) {
            NSString * tmp =  (NSString *) object ;
            self.precio.text = tmp ; 
            // do something with object
        }
//        self.precio.text = @"algo" ;
        [Data setNewMessage:@"no hay na"] ;
    }
    //self.precio.text = @"cambio colo " ;
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
- (IBAction)agregarALista:(id)sender {
    
    //int pos = [self.DetailModal[5] integerValue];
    
   // NSLog(@"el i es %i "  , pos )  ;
   // [Data addProdLista:pos ] ;
}

@end
