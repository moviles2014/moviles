//
//  main.m
//  Parcados iOS
//
//  Created by Fabio Moyano on 10/21/14.
//  Copyright (c) 2014 ___FULLUSERNAME___. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "parcadosAppDelegate.h"
#import "Data.h"

int main(int argc, char * argv[])
{
    @autoreleasepool {
        [Data getAllData];
        return UIApplicationMain(argc, argv, nil, NSStringFromClass([parcadosAppDelegate class]));
    }
}
