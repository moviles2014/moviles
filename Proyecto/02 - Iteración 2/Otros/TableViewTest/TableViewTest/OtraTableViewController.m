//
//  OtraTableViewController.m
//  TableViewTest
//
//  Created by Camilo Barraza on 9/1/14.
//  Copyright (c) 2014 Camilo Barraza. All rights reserved.
//

#import "OtraTableViewController.h"
#import "OtraTableViewCell.h"
#import "DetallesViewController.h"
#import "Data.h"

@interface OtraTableViewController ()

@end

@implementation OtraTableViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    _parqueaderos = [Data getArray];
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
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
    return _parqueaderos.count ;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    OtraTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"OtraCell" forIndexPath:indexPath];
    
    int row = [indexPath row]    ;
    cell.TitleLabel.text = [_parqueaderos[row] nombre] ;
    
    return cell;
}

-(void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    
    if ( [[segue identifier] isEqualToString:@"Detalles"] ){
        
        DetallesViewController *detailviewcontroller = [segue destinationViewController] ;
        NSIndexPath *myIndexPath = [self.tableView indexPathForSelectedRow  ] ;
        int row = [ myIndexPath row]     ;
        //detailviewcontroller.DetailModal = @[[_Producto[row] nombre]  ];
        
        
        Parqueadero *parq = _parqueaderos[row] ;
        NSString *str = [parq  nombre] ;
//        NSString *str = [NSString stringWithFormat:@"%f", [prod  precio]] ;
  //      NSLog(@"men %@" , str ) ;

    //    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
      //  [formatter setDateFormat:@"dd-MM-yyyy"];
        
        NSString *stringFromDate = [parq nombre] ;
        
        NSString* strRow = [@(row) stringValue];
        
        detailviewcontroller.DetailModal = @[[_parqueaderos[row] nombre] , [_parqueaderos[row] nombre] , [_parqueaderos[row] nombre] , str , stringFromDate , strRow ] ;
    }
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
