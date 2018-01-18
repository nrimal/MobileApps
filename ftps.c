#include <string.h>
#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>

int main() {
  int sd, connected_sd; // socket descriptors
  int rc; // return value from read
  struct sockaddr_in server_address;
  struct sockaddr_in from_address;
  char buffer[1000];
  int flags = 0;
  int file_size;
  char file_name[20];
  char new_file[26];
  struct stat st = {0};
  FILE *file;
  char *ptr;
  socklen_t from_len;

  sd = socket(AF_INET, SOCK_STREAM, flags);

  server_address.sin_family = AF_INET;
  server_address.sin_port = htons(35000);
  server_address.sin_addr.s_addr = INADDR_ANY;

  bind(sd, (struct sockaddr *) &server_address, sizeof(server_address));

  listen(sd, 5);
  connected_sd = accept(sd, (struct sockaddr *) &from_address, &from_len);

  bzero(buffer, 1000);
  rc = read(connected_sd, &buffer, 4);
  file_size = strtol(buffer, &ptr, 10);
  bzero(buffer, 1000);
  printf("File size: %d bytes.\n", file_size);

  rc = read(connected_sd, &file_name, 20);
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
    exit(EXIT_FAILURE);
  }

  while (file_size > 0) {
    rc = read(connected_sd, &buffer, 1000);
    fwrite(buffer, 1, sizeof(buffer), file);
    file_size = file_size - rc;
  }

  // send back number of bytes read

  return 0;
}
