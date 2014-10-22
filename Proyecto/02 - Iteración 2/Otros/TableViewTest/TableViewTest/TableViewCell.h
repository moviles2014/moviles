//
//  TableViewCell.h
//  TableViewTest
//
//  Created by Camilo Barraza on 9/1/14.
//  Copyright (c) 2014 Camilo Barraza. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TableViewCell : UITableViewCell

@property (strong, nonatomic) IBOutlet UILabel *TitleLabel ;
@property (strong, nonatomic) IBOutlet UILabel *DescriptionLabel ;
@property (strong, nonatomic) IBOutlet UILabel *precio ;
@property (strong, nonatomic) IBOutlet UILabel *fecha ;

@end
