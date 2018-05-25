#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>

struct Node
{
    int value;
    struct Node *next;
};

struct List
{
    int size;
    struct Node *head;
};

struct List *newList()
{
    struct List *list = malloc(sizeof(struct List));
    list->head = NULL;
    list->size = 0;
    return list;
}

void addItem(struct List *list, int value)
{
    if (list->size != 0)
    {
        struct Node *temporary = list->head;
        while (temporary->next != NULL)
        {
            temporary = temporary->next;
        }

        temporary->next = malloc(sizeof(struct List));
        temporary->next->next = NULL;
        temporary->next->value = value;
        list->size++;
    }
    else
    {
        list->head = malloc(sizeof(struct Node));
        list->head->value = value;
        list->head->next = NULL;
        list->size = 1;
    }
}

int getItem(struct List *list, int position)
{
    if (position >= list->size)
    {
        printf("No element exist, result: ");
        return 0;
    }

    struct Node *temporary = list->head;
    int counter = 0;

    while (counter < position)
    {
        temporary = temporary->next;
        counter++;
    }

    return temporary->value;
}

int getPosition(struct List *list, int item)
{
    if (list->size == 0)
    {
        printf("List is empty, result: ");
        return 0;
    }

    struct Node *temporary = list->head;
    int counter = 0;

    while (temporary->value != item)
    {
        temporary = temporary->next;
        counter++;
        if (temporary == NULL)
        {
            printf("No such element, result: ");
            return 0;
        }
    }
    return counter;
}

void deleteItem(struct List *list, int position)
{
    if (position >= list->size)
    {
        printf("No element to delete exist.\n");
        return;
    }

    if (position == 0)
    {
        struct Node *temporary = list->head;
        list->head = temporary->next;
        list->size--;
        free(temporary);
        return;
    }

    struct Node *temporary = list->head;
    struct Node *previous = NULL;
    int counter = 0;

    while (counter < position)
    {
        previous = temporary;
        temporary = temporary->next;
        counter++;
    }

    previous->next = temporary->next;
    list->size--;
    free(temporary);
}

void printList(struct List *list)
{
    struct Node *temporary = list->head;
    printf("[ ");

    while (temporary->next != NULL)
    {
        printf("%d ][ ", temporary->value);
        temporary = temporary->next;
    }

    printf("%d ]\n", temporary->value);
}

struct List *merge(struct List *a, struct List *b)
{
    struct List *c = newList();
    struct Node *temporary = a->head;

    while (temporary != NULL)
    {
        addItem(c, temporary->value);
        temporary = temporary->next;
    }

    temporary = b->head;

    while (temporary != NULL)
    {
        addItem(c, temporary->value);
        temporary = temporary->next;
    }

    return c;
}

void checkTime()
{

    struct List *timeList = newList();
    for (int i = 0; i < 10000; i++)
    {
        addItem(timeList, rand() % 10000);
    }

    double start, end;
    int selected;
    struct timeval first, second;

    gettimeofday(&first, NULL);
    for (int i = 0; i < 10000; i++)
    {
        selected = getItem(timeList, 5000);
    }
    gettimeofday(&second, NULL);

    printf("Selected: %f time units (second*10^-2)\n",
           (double)(second.tv_usec - first.tv_usec) / 1000000 +
               (double)(second.tv_sec - first.tv_sec));

    gettimeofday(&first, NULL);
    for (int i = 0; i < 10000; i++)
    {
        selected = getItem(timeList, rand() % 10000);
    }
    gettimeofday(&second, NULL);

    printf("Random: %f  time units (second*10^-2)\n",
           (double)(second.tv_usec - first.tv_usec) / 100000 +
               (double)(second.tv_sec - first.tv_sec));
}

int main()
{
    // jakby trzeba bylo lepszego losowania :v
    // int rng;
    // int urnd = open("/dev/urandom", O_RDONLY);
    // read(urnd, &rng, sizeof(int));
    // close(urnd);
    // printf("%d\n", rng);

    struct List *list = newList();
    addItem(list, 1);
    addItem(list, 2);
    addItem(list, 3);
    addItem(list, 4);
    printList(list);

    printf("%d\n", getItem(list, 2));
    printf("%d\n", getItem(list, 20));

    addItem(list, 100);
    addItem(list, 200);
    printList(list);

    deleteItem(list, 1);
    printList(list);
    deleteItem(list, 20);
    printList(list);
    deleteItem(list, 0);
    printList(list);

    printf("%d\n", getPosition(list, 0));
    printf("%d\n", getPosition(list, 100));

    struct List *BigList = newList();
    for (int i = 0; i < 1000; i++)
    {
        addItem(BigList, rand() % 1000);
    }
    printList(BigList);

    printf("\n\n");

    struct List *mergeList = merge(list, BigList);
    printList(mergeList);

    printf("\n\n");

    checkTime();
    return 0;
}