#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>

typedef int bool;
#define true 1
#define false 0

struct Node
{
    int value;
    struct Node *next;
    struct Node *prev;
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
        struct Node *temporary = malloc(sizeof(struct Node));

        temporary->value = value;
        temporary->next = list->head;
        temporary->prev = list->head->prev;
        list->head->prev->next = temporary;
        list->head->prev = temporary;
        list->size++;
    }
    else
    {
        struct Node *temporary = malloc(sizeof(struct Node));
        temporary->prev = temporary;
        temporary->next = temporary;
        temporary->value = value;
        list->head = temporary;
        list->size = 1;

        return;
    }
}

int getItem(struct List *list, int position)
{
    if (position >= list->size)
    {
        printf("No element exist, result: ");
        return 0;
    }

    int counter = 0;
    struct Node *temporary = list->head;

    if (position < (list->size / 2))
    {
        while (counter != position)
        {
            temporary = temporary->next;
            counter++;
        }
    }
    else
    {
        counter = list->size - 1;
        temporary = list->head->prev;

        while (counter != position)
        {
            temporary = temporary->prev;
            counter--;
        }
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

    while (counter < list->size)
    {
        if (temporary->value == item)
        {
            return counter;
        }
        temporary = temporary->next;
        counter++;
    }
    printf("No such element, result: ");
    return 0;
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
        list->size--;

        temporary->prev->next = temporary->next;
        temporary->next->prev = temporary->prev;
        list->head = temporary->next;

        free(temporary);
        return;
    }

    int counter = 0;
    struct Node *temporary = list->head;

    if (position < (list->size / 2))
    {

        while (counter != position)
        {
            temporary = temporary->next;
            counter++;
        }
        list->size--;
        temporary->prev->next = temporary->next;
        temporary->next->prev = temporary->prev;

        free(temporary);
    }
    else
    {
        counter = list->size - 1;
        temporary = list->head->prev;

        while (counter != position)
        {
            temporary = temporary->prev;
            counter--;
        }

        list->size--;
        temporary->prev->next = temporary->next;
        temporary->next->prev = temporary->prev;

        free(temporary);
    }
}

void printList(struct List *list, bool forward)
{

    struct Node *temporary;
    int counter = 0;
    printf("[ ");

    if (forward)
    {
        temporary = list->head;
    }
    else
    {
        temporary = list->head->prev;
    }
    while (counter < (list->size - 1))
    {
        printf("%d ][ ", temporary->value);
        if (forward)
        {
            temporary = temporary->next;
        }
        else
        {
            temporary = temporary->prev;
        }
        counter++;
    }
    printf("%d ]\n", temporary->value);
}

struct List *merge(struct List *a, struct List *b)
{
    struct List *c = newList();
    struct Node *temporary = a->head;
    int counter = 0;

    while (counter < a->size)
    {
        addItem(c, temporary->value);
        temporary = temporary->next;
        counter++;
    }

    temporary = b->head;
    counter = 0;

    while (counter < b->size)
    {
        addItem(c, temporary->value);
        temporary = temporary->next;
        counter++;
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
    printList(list, true);

    printf("%d\n", getItem(list, 2));
    printf("%d\n", getItem(list, 20));

    addItem(list, 100);
    addItem(list, 200);
    printList(list, true);

    deleteItem(list, 1);
    printList(list, true);
    deleteItem(list, 20);
    printList(list, false);
    deleteItem(list, 0);
    printList(list, true);

    printf("%d\n", getPosition(list, 0));
    printf("%d\n", getPosition(list, 100));

    struct List *BigList = newList();
    for (int i = 0; i < 50; i++)
    {
        addItem(BigList, rand() % 1000);
    }
    printList(BigList, false);

    printf("\n\n");

    struct List *mergeList = merge(list, BigList);
    printList(mergeList, true);

    printf("\n\n");

    checkTime();
    return 0;
}