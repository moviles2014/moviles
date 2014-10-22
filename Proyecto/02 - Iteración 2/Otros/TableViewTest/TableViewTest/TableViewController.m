//
//  TableViewController.m
//  TableViewTest
//
//  Created by Camilo Barraza on 9/1/14.
//  Copyright (c) 2014 Camilo Barraza. All rights reserved.
//

#import "TableViewController.h"
#import "TableViewCell.h"
#import "DetailViewController.h"
#import "Data.h"

@interface TableViewController ()

@end

@implementation TableViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (IBAction)generarLista:(id)sender {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Enter Name"
                                                    message:@"  "
                                                   delegate:self
                                          cancelButtonTitle:@"Cancel"
                                          otherButtonTitles:@"OK", nil];
    alert.alertViewStyle = UIAlertViewStylePlainTextInput;
    [alert show];
   /* for ( Producto *prod in _lista)  {
        if ( )
    }
    [self.tableView reloadData] ;*/
}
- (IBAction)mostrarTodos:(id)sender {
    
    _lista = [Data getArrayLista];
    
    
    
    
    
    float total = 0.0f ;

        
    }
    
    NSString *str = [NSString stringWithFormat:@"%f", total];
    self.costo.text = str ;
    
    
    [self.tableView reloadData] ;
    
}


- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex == 1) {
        NSString *fecha = [alertView textFieldAtIndex:0].text;
        
        NSDateFormatter *df = [[NSDateFormatter alloc] init];
        [df setDateFormat:@"dd-MM-yyyy"];
        NSDate *myDate = [df dateFromString: fecha];
        
        
        
        NSMutableArray *myArray = [[NSMutableArray alloc] init];

    }
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    
   
    
    
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
}

-(void) viewDidAppear:(BOOL)animated {
    _lista = [Data getArrayLista];
    
    
    
    
    }
    NSString *str = [NSString stringWithFormat:@"%f", total];
    
    NSLog(@"pilla %@" , str ) ;
   
    self.costo.text = str ; 
    
    NSLog(@"coletisimo") ;
    [self.tableView reloadData] ;
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{

    // Return the number of rows in the section.
    return _lista.count;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{

    TableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"TableViewCell" forIndexPath:indexPath];
    
    int row = [indexPath row]    ;
    cell.TitleLabel.text = [ _lista[row] nombre ];
    cell.DescriptionLabel.text = [ _lista[row] categoria ];
    
    
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateFormat:@"dd-MM-yyyy"];
    Producto *prod = _lista [row ];
    NSString *stringFromDate = [formatter stringFromDate: [prod fecha]];

    
    cell.fecha.text = stringFromDate  ;
    
    Producto *tmp =  _lista[row ] ;
    float pr = [ tmp precio ]  ;
    
    NSString *str = [NSString stringWithFormat:@"%f", pr];
    cell.precio.text = str  ;


    
    return cell;
}

- (void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    NSLog(@"coleto 2") ;

    if ( [[segue identifier] isEqualToString:@"ShowDetails"] ){
        DetailViewController *detailviewcontroller = [segue destinationViewController] ;
        NSIndexPath *myIndexPath = [self.tableView indexPathForSelectedRow  ] ;
        int row = [ myIndexPath row]     ;
        detailviewcontroller.DetailModal = @[_lista[row]] ;
        
    }
      //  [self.tableView reloadData] ;

}
- (IBAction)action:(id)sender {
    [Data getAllData ] ;
    [Data addName:@"el covas"] ;
    NSMutableArray *arr = [Data getArray]   ;
    NSLog(@"el count del array es %i" , [arr count]) ;
}

- (IBAction)reload:(id)sender {
    [self.tableView reloadData] ;
    NSLog(@"entro")  ;
}


/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
