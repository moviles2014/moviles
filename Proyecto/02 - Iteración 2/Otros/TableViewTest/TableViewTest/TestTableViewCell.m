//
//  TestTableViewCell.m
//  TableViewTest
//
//  Created by FABIO ENRIQUE MOYANO GALKIN on 9/2/14.
//  Copyright (c) 2014 FABIO ENRIQUE MOYANO GALKIN. All rights reserved.
//

#import "TestTableViewCell.h"

@implementation TestTableViewCell

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)awakeFromNib
{
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
