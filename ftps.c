#include <string.h>
#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>

int main() {
  int sd, connected_sd; // socket descriptors
  int rc; // return value from read
  struct sockaddr_in server_address;
  struct sockaddr_in from_address;
  char buffer[100];
  int flags = 0;
  socklen_t from_len;
  
  sd = socket(AF_INET, SOCK_STREAM, flags);
 
  server_address.sin_family = AF_INET;
  server_address.sin_port = htons(24000);
  server_address.sin_addr.s_addr = INADDR_ANY;
 
  bind(sd, (struct sockaddr *) &server_address, sizeof(server_address));

  listen(sd, 5);
  connected_sd = accept(sd, (struct sockaddr *) &from_address, &from_len);
  bzero(buffer, 100);
  rc = read(connected_sd, &buffer, 100);
 
  printf("received the following %s\n", buffer);

  return 0;
}
