#include <string.h>
#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <time.h>

#define TIMEOUT 30

int findFileSize(FILE* to_transfer){
	
	int EndOfFile = -1;
	//int writeCheck = 0;

	//Seeks the end of the input file, outputs if there is an error,  then rewinds the input file stream to the beginning
	if(fseek(to_transfer, 0L, SEEK_END) == -1){
		printf("Trouble seeking the end of the file to transfer\n");
		exit(1);
	} else{
		long length = ftell(to_transfer);
		EndOfFile = (int)length;
		rewind(to_transfer);
	}
	return EndOfFile;
}

int main(int argc, char* argv[]){
	int sd;
	struct sockaddr_in server_address;
	char buffer[1000];
	bzero(buffer, 1000);
	int* received = malloc(sizeof(int));
	memset(received, 0, sizeof(int));
	int portNumber; //user specified port number
	char serverIP[29]; //user specified IP
	int rc = 0; //return code
	int file_size; //size of file
	int file_size_send = 0;
	int file_size_ack = 0;
	int bytes_read_ack = 0;
	char* file_name; //input file name
	time_t start, current_time; //Used to monitor connection time out
	int connected = 0; 

	if(argc < 4){
		printf("Usage is ./ftpc <remote-IP> <remote-port> <local-file-to-transfer>\n");
		exit(1);
	}

	sd = socket(AF_INET, SOCK_STREAM, 0);

	file_name = argv[3];
	portNumber = strtol(argv[2], NULL, 10);
	strcpy(serverIP, argv[1]); //Careful of using str functions with bin data

	FILE* file_to_transfer = fopen(file_name, "rb");
	if(file_to_transfer == NULL){
		printf("Error cannot open file provided\n");
		exit(1);
	}
	//Writes file size to output file and returns file size to the variable fileSize
	file_size = findFileSize(file_to_transfer);
	printf("Size of file is = %d\n", file_size);

	server_address.sin_family = AF_INET;
	server_address.sin_port = htons(portNumber);
	server_address.sin_addr.s_addr = inet_addr(serverIP);

	start = time(NULL);
	while(connected == 0){
		if(connect (sd, (struct sockaddr*) &server_address, sizeof(struct sockaddr_in)) < 0){
			current_time = time(NULL);
		}else{
			connected = 1;
		}
		if(connected == 0 && current_time - start >= TIMEOUT){
			perror("Error connecting stream socket client has timed out");
			exit(1);
		}
	}

	rc = write(sd, &file_size, sizeof(int));
	if(rc < 0){
		perror("Error file size not sent");
	}
	printf("Client sent %d bytes\n", rc); //Should be 4	
	file_size_send = rc;

	rc = read(sd, &file_size_ack, sizeof(int));
	if(rc < 0){
		perror("Error server file size ack not received");
	}
	printf("Server received %d bytes\n", rc); //Should be 4	
	
	if(file_size_send != file_size_ack){
		printf("Error server did not receive correct file size\n");
		exit(1);
	}

	rc = write(sd, file_name, 20);
	if(rc < 0){
		perror("Error file name not sent");
	}
	printf("Client sent %d bytes\n", rc);

	long pos = ftell(file_to_transfer);
	while((int)pos < file_size){
	//		memset(received, 0, sizeof(int));

		int items = fread(buffer, 1, 1000, file_to_transfer);
		if(items == 0){
			printf("Error could not read items in from file to transfer\n");
		}
		rc = write(sd, buffer, items); //End of buffer hopefully won't write garbage
		if(rc < 0){
			perror("Error client could not write from file buffer");
		}
		printf("Client sent %d bytes\n", rc);

		pos = ftell(file_to_transfer);
	}

	rc = read(sd, &bytes_read_ack, sizeof(int));
	if(rc < 0){
		perror("Error server file bytes read ack not received");
	}
	printf("Server received %d bytes from the file transferred\n", bytes_read_ack); //Should be file_size	
	
return 0;
}
