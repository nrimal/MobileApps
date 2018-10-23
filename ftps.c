#include <string.h>
#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>

int main(int argc, char* argv[]) {
  int sd, connected_sd; // socket descriptors
  int rc; // return value from read
  struct sockaddr_in server_address;
  struct sockaddr_in from_address;
  char buffer[1000];
  int flags = 0;
  int file_size;
  int port = 0;
  char file_name[20] = {0};
  char new_file[26];
  struct stat st = {0};
  FILE *file;
  char *ptr;
  int* size = malloc(sizeof(int));
  socklen_t from_len;

  if(argc < 2){
	printf("Usage is ./ftps <local-port>\n");
	exit(1);
  }
  port = atoi(argv[1]);

  sd = socket(AF_INET, SOCK_STREAM, flags);

  server_address.sin_family = AF_INET;
  server_address.sin_port = htons(port);
  server_address.sin_addr.s_addr = INADDR_ANY;

  bind(sd, (struct sockaddr *) &server_address, sizeof(server_address));

  //sleep(50);
  //sleep(20);

  listen(sd, 5);

  //sleep(50);

  connected_sd = accept(sd, (struct sockaddr *) &from_address, &from_len);

  bzero(buffer, 1000);

  rc = read(connected_sd, size, sizeof(int));
  if(rc < 0){
	printf("Error did not received file size\n");
	exit(1);
  }

  file_size = *size;
  printf("File size: %d bytes.\n", file_size);

  rc = write(connected_sd, &rc, sizeof(int));
  if(rc < 0){
	printf("Error did not send server ack\n");
	exit(1);
  }

  rc = read(connected_sd, &file_name, 20);
  if(rc < 0){
	printf("Error did not received file name\n");
	exit(1);
  }
  printf("Filename: %s\n", file_name);

  if (stat("recvd", &st) == -1) {
    if (mkdir("recvd", 0777) != 0) {
      printf("Error creating directory.");
    }
  }

  strcpy(new_file, "recvd/");
  strcat(new_file, file_name);
  file = fopen(new_file, "wb");

  if (file == NULL) {
    perror("Error opening copy file.");
    exit(1);
  }

  int fileBytesReceived = 0;
  while (fileBytesReceived < file_size) {
    rc = read(connected_sd, &buffer, 1000);
	  if(rc < 0){
		printf("Error did not receive all file bytes\n");
		exit(1);
	  }
    fileBytesReceived = fileBytesReceived + rc;
    if(fwrite(buffer, 1, rc, file) == 0){
	  printf("Error could not write to ouput file\n");
	  exit(1);
	  }
    bzero(buffer,1000);
  }

  rc = write(connected_sd, &fileBytesReceived, sizeof(int));
  if(rc < 0){
	printf("Error did not send server ack\n");
	exit(1);
  }

  // send back number of bytes read

  free(size);

  return 0;
}
