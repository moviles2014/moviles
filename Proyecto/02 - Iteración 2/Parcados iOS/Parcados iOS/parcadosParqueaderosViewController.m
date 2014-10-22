//
//  parcadosParqueaderosViewController.m
//  Parcados iOS
//
//  Created by Fabio Moyano on 10/21/14.
//  Copyright (c) 2014 Fabio Moyano. All rights reserved.
//

#import "parcadosParqueaderosViewController.h"
#import "parcadosParqueaderosViewCell.h"
#import "parcadosParqueaderosListaViewController.h"
#import "Data.h"
#import "Empresa.h"

@interface parcadosParqueaderosViewController ()

@end

@implementation parcadosParqueaderosViewController

-(NSMutableArray *) darLista
{
    return [Data getLista];
}

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
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    
    return [[self darLista] count];
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    parcadosParqueaderosViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"ParqueaderoCell" forIndexPath:indexPath];
    
    int row = [indexPath row] ;

    cell.TitleLabel.text = [[self darLista] objectAtIndex:row];
    
    return cell;
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


#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    
    
    
    if ([[segue identifier] isEqualToString:@"ParqueaderosLista"])
    {
        // Get reference to the destination view controller
        parcadosParqueaderosListaViewController *vc = [segue destinationViewController];
        
        // Pass any objects to the view controller here, like...
        NSIndexPath *path = [self.tableView indexPathForSelectedRow];
        NSUInteger lastIndex = [path indexAtPosition:[path length] - 1];
        
        
        [vc setParqueaderos:[[Data darEmpresaID:lastIndex] getArrayParqueaderos]];
    }
    
    
    // Pass the selected object to the new view controller.
}

@end
