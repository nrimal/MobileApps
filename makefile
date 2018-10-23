#makefile for Lab 2

CC=gcc
CFLAGS = -g -Wall

all: ftps ftpc

ftps: ftps.c
	$(CC) $(CFLAGS) -o ftps ftps.c

ftpc: ftpc.c
	$(CC) $(CFLAGS) -o ftpc ftpc.c

clean:
	rm ftps ftpc
