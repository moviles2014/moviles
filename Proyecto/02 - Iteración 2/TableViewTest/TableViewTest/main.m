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
#import "PNImports.h"

int main(int argc, char * argv[])
{
    @autoreleasepool {
        [Data getAllData] ;
        NSLog(@"testiando") ;
        
        [PubNub setConfiguration:[PNConfiguration configurationWithPublishKey:@"pub-c-08e15781-225a-4935-a61f-bcafefa86481" subscribeKey:@"sub-c-414c745c-5273-11e4-a191-02ee2ddab7fe"  secretKey:@"" ]];
        
        [PubNub connect] ;
        
        PNChannel *channel_1 = [PNChannel channelWithName:@"parcados" ] ;
        
        [PubNub subscribeOnChannel:channel_1] ;
        
        [PubNub sendMessage:@"Impark Carrera 35;101;121" toChannel:channel_1] ;
        
        
        return UIApplicationMain(argc, argv, nil, NSStringFromClass([AppDelegate class]));
    }
}
