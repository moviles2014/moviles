//
//  main.m
//  TableViewTest
//
//  Created by Camilo Barraza on 9/1/14.
//  Copyright (c) 2014 ___FULLUSERNAME___. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "AppDelegate.h"
#import "Data.h"

int main(int argc, char * argv[])
{
    @autoreleasepool {
        [Data getAllData] ;
        return UIApplicationMain(argc, argv, nil, NSStringFromClass([AppDelegate class]));
    }
}
