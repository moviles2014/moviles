//
//  TableViewController.h
//  TableViewTest
//
//  Created by Camilo Barraza on 9/1/14.
//  Copyright (c) 2014 Camilo Barraza. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TableViewController : UITableViewController

@property (nonatomic , strong) NSArray *lista ;
@property (nonatomic , strong) NSArray *Description ;
@property (weak, nonatomic) IBOutlet UITextField *costo;



@end
